# Guía Definitiva: PostgreSQL con Docker y Spring Boot

Esta guía consolida los conceptos arquitectónicos, configuración paso a paso y resolución de errores comunes al trabajar con bases de datos PostgreSQL en contenedores Docker para desarrollo en Spring Boot.

---

## 1. Fundamentos Arquitectónicos: ¿Por qué Docker por proyecto?

* **Aislamiento de Dependencias:** Evita instalar un servidor PostgreSQL global en el sistema operativo host (ej. Manjaro/Arch) que pueda corromperse o desactualizarse con upgrades del sistema (`pacman -Syu`).
* **Soporte Multi-versión:** Permite ejecutar diferentes versiones de PostgreSQL (14, 15, 16) o extensiones específicas en paralelo sin conflictos.
* **Infraestructura como Código (IaC):** La configuración del servicio (`compose.yaml`) reside en el repositorio del proyecto. Cualquier miembro del equipo puede clonar el repositorio y levantar el entorno con un único comando.
* **Ciclo de Vida Controlado:** Los datos persisten mediante volúmenes independientes del ciclo de vida del contenedor.

---

## 2. Configuración Inicial del Sistema Operativo (Linux / Manjaro)

Requisitos previos a nivel de sistema operativo (ejecutar una sola vez):

```bash
# 1. Instalar Docker y el plugin de Compose
sudo pacman -S docker docker-compose

# 2. Habilitar e iniciar el servicio del daemon de Docker
sudo systemctl enable --now docker

# 3. Incorporar el usuario actual al grupo docker
sudo usermod -aG docker $USER
```

> **Nota:** Para que los permisos del grupo `docker` surtan efecto de forma global en todas las terminales y sesiones del entorno gráfico, es necesario **cerrar la sesión de usuario o reiniciar el sistema**.

---

## 3. Configuración del Proyecto (La Trinidad)

### A. Archivo `compose.yaml` (Raíz del proyecto, junto a `pom.xml`)

```yaml
services:
  postgres:
    image: postgres:16-alpine
    container_name: agenda_hunter_db
    restart: unless-stopped
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: hunter
      POSTGRES_PASSWORD: hunter_password
      POSTGRES_DB: agenda_hunter
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

### B. Archivo `src/main/resources/application.yml`

Las credenciales, el puerto y el nombre de la base de datos deben corresponder exactamente con los definidos en `compose.yaml`:

```yaml
spring:
  application:
    name: agendahunter

  datasource:
    url: jdbc:postgresql://localhost:5432/agenda_hunter
    username: hunter
    password: hunter_password
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: update # Usar 'update' en fase de diseño; 'validate' cuando existan migraciones Flyway
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  flyway:
    enabled: true
    clean-on-validation-error: true
```

> **Regla Crítica:** No mantener `application.properties` y `application.yml` simultáneamente si contienen propiedades duplicadas, ya que `.properties` suele tomar precedencia y sobreescribir valores en tiempo de arranque.

### C. Dependencias Maven (`pom.xml`)

```xml
<dependencies>
    <!-- Driver oficial JDBC de PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Spring Data JPA (incluye Hibernate ORM y pool HikariCP) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Migraciones con Flyway (opcional pero recomendado en producción) -->
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-database-postgresql</artifactId>
    </dependency>
</dependencies>
```

---

## 4. Ciclo de Vida y Estrategia de Migración: `update` vs `validate`

| Estrategia | Cuándo utilizarlo | Comportamiento técnico |
| :--- | :--- | :--- |
| **`ddl-auto: update`** | Prototipado y desarrollo inicial | Hibernate inspecciona las entidades `@Entity` y crea o añade columnas automáticamente sin tocar datos existentes. **No elimina columnas obsoletas**. |
| **`ddl-auto: validate`** | Staging, Producción y con Flyway | Hibernate no realiza mutaciones DDL sobre la base. Solo valida que las tablas y tipos coincidan con el código Java. Si falta una tabla, aborta el arranque. |

> **Advertencia de Producción:** Nunca utilizar `ddl-auto: update` en ambientes productivos. Las migraciones estructurales deben ser idempotentes, versionadas y auditadas mediante scripts SQL con Flyway o Liquibase.

---

## 5. Flujo Operativo Diario Multi-Proyecto

```
[ Proyecto A (Agenda) ]                    [ Proyecto B (E-commerce) ]
        │                                                │
        ▼                                                ▼
docker compose up -d                             docker compose up -d
 (Usa puerto 5432)                                (Usa puerto 5432)
        │                                                │
  [ Trabajar ]                                     [ Trabajar ]
        │                                                │
        ▼                                                ▼
docker compose stop                              docker compose stop
(Libera puerto 5432)                             (Libera puerto 5432)
```

### Comandos de Gestión:

1. **Iniciar servicio en segundo plano:**
   ```bash
   docker compose up -d
   ```
2. **Detener servicio liberando el puerto host (Conserva datos):**
   ```bash
   docker compose stop
   ```
3. **Destruir contenedor y purgar volumen de datos (Reset total):**
   ```bash
   docker compose down -v
   ```

---

## 6. Matriz de Diagnóstico y Resolución de Errores

### Error 1: Permisos denegados en el socket
* **Mensaje:** `permission denied while trying to connect to the docker API at unix:///var/run/docker.sock`
* **Causa:** La sesión actual no cargó los grupos complementarios tras ejecutar `usermod`.
* **Solución Inmediata:** Ejecutar `newgrp docker` en la terminal activa.
* **Solución Permanente:** Cerrar sesión en el gestor de ventanas de Linux o reiniciar la máquina.

### Error 2: Conflicto de puertos en el Host
* **Mensaje:** `Bind for 0.0.0.0:5432 failed: port is already allocated`
* **Causa:** Existe otro proceso o contenedor reteniendo el puerto TCP 5432.
* **Solución:**
  ```bash
  docker ps
  docker stop <nombre_o_id_del_contenedor_antiguo>
  # O forzar eliminación:
  docker rm -f <nombre_o_id_del_contenedor_antiguo>
  ```

### Error 3: Ausencia de credenciales en la autenticación
* **Mensaje:** `The server requested SCRAM-based authentication, but no password was provided.`
* **Causa:** Spring Boot inició el pool de conexiones sin contraseña (valor nulo o vacío). Ocurre por tabulaciones ilegales en el YAML o por falta de sincronización en `target/classes`.
* **Solución:** Ejecutar `./mvnw clean compile` y verificar que las propiedades no usen caracteres `\t`.

### Error 4: Conexión rechazada
* **Mensaje:** `Connection to localhost:5432 refused.`
* **Causa:** No existe ningún proceso escuchando en el puerto 5432. El contenedor se encuentra detenido o sufrió un crash.
* **Solución:**
  ```bash
  docker ps -a
  docker compose up -d
  # Revisar salida de error si no arranca:
  docker logs agenda_hunter_db
  ```

### Error 5: Validación de esquema fallida
* **Mensaje:** `SchemaManagementException: Schema validation: missing table [nombre_tabla]`
* **Causa:** La base de datos es nueva y se encuentra vacía, pero Hibernate está configurado con `ddl-auto: validate`.
* **Solución:** Cambiar transitoriamente a `ddl-auto: update` en `application.yml` para generar el esquema inicial o suministrar los scripts correspondientes en `src/main/resources/db/migration/V1__init.sql`.
