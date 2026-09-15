package com.emersondev.agendahunter.domain.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Idea {
    private UUID id;
    private UUID practicanteId;
    private String contenido;
    private LocalDateTime fechaCaptura;
    private boolean procesada;

    // Constructor para una Idea nueva
    public Idea(UUID practicanteId, String contenido) {
        if (practicanteId == null) throw new IllegalArgumentException("practicanteId es obligatorio");
        if (contenido == null || contenido.isBlank()) throw new IllegalArgumentException("El contenido no puede estar vacío");

        this.id = UUID.randomUUID();
        this.practicanteId = practicanteId;
        this.contenido = contenido.trim();
        this.fechaCaptura = LocalDateTime.now();
        this.procesada = false;
    }

    // Constructor para rehidratar desde la base de datos
    public Idea(UUID id, UUID practicanteId, String contenido, LocalDateTime fechaCaptura, boolean procesada) {
        this.id = id;
        this.practicanteId = practicanteId;
        this.contenido = contenido;
        this.fechaCaptura = fechaCaptura;
        this.procesada = procesada;
    }

    // Regla de negocio: Marcar como procesada
    public void procesar() {
        this.procesada = true;
    }
}
