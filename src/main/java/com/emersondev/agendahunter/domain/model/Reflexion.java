package com.emersondev.agendahunter.domain.model;

import lombok.Getter;
import java.util.UUID;

@Getter
public class Reflexion {
    private UUID id;
    private String pregunta;
    private String respuesta;

    // Crear nueva reflexión
    public Reflexion(String pregunta, String respuesta) {
        if (pregunta == null || pregunta.isBlank()) throw new IllegalArgumentException("La pregunta es obligatoria");
        if (respuesta == null || respuesta.isBlank()) throw new IllegalArgumentException("La respuesta es obligatoria");
        
        this.id = UUID.randomUUID();
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    // Rehidratar desde DB
    public Reflexion(UUID id, String pregunta, String respuesta) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }
}
