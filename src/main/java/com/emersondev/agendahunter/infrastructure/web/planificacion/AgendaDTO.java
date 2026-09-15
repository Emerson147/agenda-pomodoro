package com.emersondev.agendahunter.infrastructure.web.planificacion;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;
@Data
public class AgendaDTO {
    private UUID practicanteId;
    private LocalDate fecha;
    private List<AgendaItemDTO> items;
}
