package com.emersondev.agendahunter.infrastructure.web.tareaenfoque;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearTareaEnfoqueRequest {
    private UUID practicanteId;
    private String titulo;
    private Integer pomodorosEstimados;
}
