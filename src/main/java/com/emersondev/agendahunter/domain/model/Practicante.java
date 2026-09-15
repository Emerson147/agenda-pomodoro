package com.emersondev.agendahunter.domain.model;

import com.emersondev.agendahunter.domain.exception.DomainException;
import lombok.Getter;
import java.util.UUID;

@Getter
public class Practicante {

    private UUID id;
    private String nombre;
    private String email;
    private String password;
    private String zonaHoraria;

    // Constructor para un Practicante nuevo
    public Practicante(UUID id, String nombre, String email, String password) {
        this.id = id != null ? id : UUID.randomUUID();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.zonaHoraria = "America/Lima";
    }

    // Constructor para rehidratar desde la base de datos
    public Practicante(UUID id, String nombre, String email, String password, String zonaHoraria) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.zonaHoraria = zonaHoraria;
    }
}
