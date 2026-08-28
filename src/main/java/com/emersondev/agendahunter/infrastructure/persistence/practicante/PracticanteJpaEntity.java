package com.emersondev.agendahunter.infrastructure.persistence.practicante;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Entidad Esclava (Infraestructura).
 * Solo existe para mapear los datos a la tabla 'practicantes' en PostgreSQL.
 * NO tiene lógica de negocio.
 */
@Entity
@Table(name = "practicantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PracticanteJpaEntity {
    @Id
    private UUID id;
    private String nombre;
    private String email;
    private String password;
    private int nivel;
    private int experiencia;
    private int racha;
    private int armonia;
    private int maleza;
    private String zonaHoraria;
}
