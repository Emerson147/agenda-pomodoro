package com.emersondev.agendahunter.infrastructure.web.planificacion;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
@Data
public class CerrarDiaRequest {
    private UUID practicanteId;
    private LocalDate fecha;
}
