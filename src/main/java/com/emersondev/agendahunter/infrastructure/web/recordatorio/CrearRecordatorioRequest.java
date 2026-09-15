package com.emersondev.agendahunter.infrastructure.web.recordatorio;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearRecordatorioRequest {
    private UUID practicanteId;
    private String titulo;
}
