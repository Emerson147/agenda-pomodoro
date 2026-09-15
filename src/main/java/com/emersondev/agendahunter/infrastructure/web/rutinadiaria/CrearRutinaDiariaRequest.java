package com.emersondev.agendahunter.infrastructure.web.rutinadiaria;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearRutinaDiariaRequest {
    private UUID practicanteId;
    private String titulo;
    private int seriesTotales;
}
