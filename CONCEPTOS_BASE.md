# 🧠 Manual de Conceptos Base (El "Por Qué" de Todo)

Este documento no explica cómo funciona *Agenda Hunter*, sino **por qué** programamos como programamos. Es tu enciclopedia de conceptos, patrones y código limpio, explicados de la forma más sencilla posible (como si tuvieras 5 años) pero con el rigor técnico de un Senior.

---

## 1. La Arquitectura Hexagonal (Explicada para 5 años)

Imaginate que tu sistema es un **Restaurante de Hamburguesas**.

*   **La Receta Secreta y el Cocinero (El Dominio):** Al cocinero le importa una sola cosa: hacer la hamburguesa perfecta siguiendo las reglas (ej: "La carne tiene que estar bien cocida"). Al cocinero **no le importa** si el pedido entró por la ventanilla, por teléfono o por PedidosYa.
*   **El Mozo y la Cajera (Controlador Web - Infraestructura):** Son los que hablan con los clientes (la web). Toman el pedido y se lo pasan a la cocina. **El mozo no cocina**.
*   **La Moto de Delivery (Persistencia / Base de Datos - Infraestructura):** Agarra la hamburguesa terminada y la lleva a destino (la guarda en la base de datos). **La moto no sabe cómo se cocinó la hamburguesa**.

**¿Por qué hacemos esto? (El Por Qué)**
Si mañana decidís cambiar PedidosYa por Rappi (cambiar Spring Boot por otro framework), o si cambiás las motos por drones (cambiás PostgreSQL por MongoDB), **la cocina sigue funcionando exactamente igual**. Tu receta (tu código de negocio) está 100% protegida y aislada de los cambios tecnológicos.

---

## 2. Los Patrones de Diseño que Usamos Todo el Tiempo

### A. Patrón DTO (Data Transfer Object) y Mappers
**¿Qué es?** Es una caja de cartón simple que usamos para transportar datos de un lugar a otro, sin enviar la "joya" original.
**¿Para qué se usa?** Si un cliente pide ver su perfil, no le mandamos la clase `Practicante` (que tiene adentro la contraseña encriptada y datos sensibles). Agarramos a `Practicante`, copiamos su Nombre y Nivel en un `PracticanteDTO` (la caja de cartón), y mandamos eso.

**❌ Lo Malo (Mandar la entidad entera):**
```java
// ¡PELIGRO! Estás mandando la contraseña a Internet
public Practicante obtenerUsuario() {
    return practicanteRepository.buscar(1); 
}
```

**✅ Lo Bueno (Usar DTO):**
```java
// Seguro. Solo viaja lo que queremos que se vea.
public PracticanteDTO obtenerUsuario() {
    Practicante p = practicanteRepository.buscar(1);
    return new PracticanteDTO(p.getNombre(), p.getNivel());
}
```
*A la acción de convertir un `Practicante` en un `PracticanteDTO` se le llama **Mapear** (usar un Mapper).*

---

### B. Patrón Repository (El Contrato de Almacenamiento)
**¿Qué es?** Es decirle al Dominio: *"Quedate tranquilo, acá te dejo una ranura vacía. No sé quién te va a traer los datos, pero alguien lo hará"*.
**¿Para qué se usa?** Para que tu código de negocio no tenga un `import java.sql.Connection` ni sepa que existe PostgreSQL. El dominio solo crea una **Interface** y la Infraestructura se encarga de rellenarla.

---

## 3. Código Limpio: Dejemos de ser "Picadores de Código"

### Regla 1: Validaciones en las fronteras (Early Return / Bouncer Pattern)
Imaginate un patovica (guardia) en un boliche. No deja pasar al borracho hasta la barra para recién ahí echarlo. Lo frena en la puerta. En código, esto significa **validar todo arriba y salir rápido**, evitando `ifs` anidados.

**❌ Lo Malo (El Código Flecha o "Hadouken"):**
```java
public void comprarItem(Practicante p, Item i) {
    if (p != null) {
        if (p.getMonedas() >= i.getPrecio()) {
            if (!p.tieneMaleza()) {
                p.descontar(i.getPrecio());
                p.darItem(i);
            } else {
                throw new Exception("Tenés maleza");
            }
        } else {
            throw new Exception("No tenés plata");
        }
    }
}
```

**✅ Lo Bueno (Early Return / Patovica):**
```java
public void comprarItem(Practicante p, Item i) {
    // Los patovicas frenan lo que está mal en la puerta
    if (p == null) throw new IllegalArgumentException("Usuario nulo");
    if (p.tieneMaleza()) throw new MalezaException("Limpiá tu jardín primero");
    if (p.getMonedas() < i.getPrecio()) throw new SinDineroException();

    // Si llegaste acá, sos VIP. Hacé la acción sin anidar nada.
    p.descontar(i.getPrecio());
    p.darItem(i);
}
```

### Regla 2: El Dominio "Anémico" vs Dominio Rico
**¿Qué es?** Un dominio "anémico" es cuando tus clases son tontas (solo tienen `getters` y `setters`) y los `Casos de Uso` hacen todas las matemáticas y validaciones. Un **Dominio Rico** (lo que nosotros usamos) es donde la clase es inteligente y se cuida a sí misma.

**❌ Lo Malo (Dominio Anémico - El UseCase hace todo):**
```java
public class CompletarTareaUseCase {
    public void ejecutar(Tarea tarea) {
        // El caso de uso está calculando las reglas de negocio. ¡MAL!
        if (tarea.getMinutos() > 25 && tarea.getEstado().equals("INICIADA")) {
            tarea.setEstado("COMPLETADA");
            tarea.setRecompensa(100);
        }
    }
}
```

**✅ Lo Bueno (Dominio Rico - La clase manda):**
```java
public class CompletarTareaUseCase {
    public void ejecutar(Tarea tarea) {
        // El caso de uso solo da la orden
        tarea.completar(); 
    }
}

// Y dentro de la clase Tarea (Dominio Rico):
public class Tarea {
    public void completar() {
        if (this.minutos < 25) throw new MuyProntoException();
        if (!this.estado.equals("INICIADA")) throw new EstadoInvalidoException();
        
        this.estado = "COMPLETADA";
        this.recompensa = 100;
    }
}
```

---

## 4. SOLID Súper Simplificado

Solo vamos a explicar la D (Dependency Inversion), que es la que hace posible todo tu sistema.

**Inversión de Dependencias (D):**
Imaginate un enchufe de pared. Tu computadora no se suelda directamente a los cables pelados de la pared. Usa un enchufe estándar (Interface). Así, mañana te mudás a otra casa y, siempre que haya un enchufe estándar, tu compu prende.
*   **En código:** Una clase de tu Dominio nunca debe hacer un `new AdaptadorPostgres()`. Debe pedir un `Repositorio` (el enchufe), y a través de Spring (Inyección de Dependencias), el sistema se encarga de enchufar la base de datos correcta al arrancar.

---
*Si no entendés el por qué de una tecnología, patrón o forma de programar, vení a buscarlo acá. Si no está, preguntale a la IA que te lo explique como a un chico de 5 años y agregalo.*
