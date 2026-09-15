package com.emersondev.agendahunter.infrastructure.web.tareaenfoque;
import lombok.Data;
@Data
public class IniciarCicloRequest {
    private int duracionMinutos;
    private String tipo;
}
