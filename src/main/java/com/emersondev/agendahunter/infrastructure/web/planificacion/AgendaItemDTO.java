package com.emersondev.agendahunter.infrastructure.web.planificacion;
import java.util.UUID;
import lombok.Data;
@Data
public class AgendaItemDTO {
    private UUID id;
    private String titulo;
    private String tipo;
    private boolean completado;
    private String faseDia;
    private String horaProgramada;
}
