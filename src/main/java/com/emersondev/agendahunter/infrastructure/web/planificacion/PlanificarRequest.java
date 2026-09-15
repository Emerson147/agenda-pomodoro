package com.emersondev.agendahunter.infrastructure.web.planificacion;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
@Data
public class PlanificarRequest {
    private UUID practicanteId;
    private UUID itemId;
    private LocalDate fecha;
    private String faseDia;
    private String horaProgramada;
}
