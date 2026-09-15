package com.emersondev.agendahunter.infrastructure.web.planificacion;

import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarRecordatorioUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarTareaEnfoqueUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarRutinaDiariaUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.CerrarDiaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;

@RestController
@RequestMapping("/api/v1/planificacion")
@RequiredArgsConstructor
public class PlanificacionController {
    private final PlanificarRecordatorioUseCase planificarRecordatorioUseCase;
    private final PlanificarTareaEnfoqueUseCase planificarTareaEnfoqueUseCase;
    private final PlanificarRutinaDiariaUseCase planificarRutinaDiariaUseCase;
    private final CerrarDiaUseCase cerrarDiaUseCase;
    private final CalendarioSiembraRepository calendarioSiembraRepository;

    @PostMapping("/recordatorio")
    public ResponseEntity<Void> planificarRecordatorio(@RequestBody PlanificarRequest req) {
        planificarRecordatorioUseCase.ejecutar(req.getPracticanteId(), req.getItemId(), req.getFecha(), req.getFaseDia(), req.getHoraProgramada());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/enfoque")
    public ResponseEntity<Void> planificarEnfoque(@RequestBody PlanificarRequest req) {
        planificarTareaEnfoqueUseCase.ejecutar(req.getPracticanteId(), req.getItemId(), req.getFecha(), req.getFaseDia(), req.getHoraProgramada());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/rutina")
    public ResponseEntity<Void> planificarRutina(@RequestBody PlanificarRequest req) {
        planificarRutinaDiariaUseCase.ejecutar(req.getPracticanteId(), req.getItemId(), req.getFecha(), req.getFaseDia(), req.getHoraProgramada());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cerrar-dia")
    public ResponseEntity<Void> cerrarDia(@RequestBody CerrarDiaRequest req) {
        cerrarDiaUseCase.ejecutar(req.getPracticanteId(), req.getFecha());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/agenda/{practicanteId}/{fecha}")
    public ResponseEntity<AgendaDTO> obtenerAgenda(
            @PathVariable UUID practicanteId,
            @PathVariable LocalDate fecha) {
            
        CalendarioSiembra planificacion = calendarioSiembraRepository
                .buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElse(null);

        if (planificacion == null) {
            return ResponseEntity.notFound().build();
        }

        AgendaDTO dto = new AgendaDTO();
        dto.setPracticanteId(practicanteId);
        dto.setFecha(fecha);
        dto.setItems(new ArrayList<>());

        planificacion.getRecordatorios().forEach(r -> {
            AgendaItemDTO item = new AgendaItemDTO();
            item.setId(r.getId());
            item.setTitulo(r.getTitulo());
            item.setTipo("RECORDATORIO");
            item.setCompletado(r.isCompletado());
            item.setFaseDia(r.getFaseDia());
            item.setHoraProgramada(r.getHoraProgramada());
            dto.getItems().add(item);
        });

        planificacion.getTareasEnfoque().forEach(t -> {
            AgendaItemDTO item = new AgendaItemDTO();
            item.setId(t.getId());
            item.setTitulo(t.getTitulo());
            item.setTipo("ENFOQUE");
            item.setCompletado(t.isCompletado());
            item.setFaseDia(t.getFaseDia());
            item.setHoraProgramada(t.getHoraProgramada());
            dto.getItems().add(item);
        });

        planificacion.getRutinasDiarias().forEach(r -> {
            AgendaItemDTO item = new AgendaItemDTO();
            item.setId(r.getId());
            item.setTitulo(r.getTitulo());
            item.setTipo("RUTINA");
            item.setCompletado(r.getSeriesCompletadas() >= r.getSeriesTotales());
            item.setFaseDia(r.getFaseDia());
            item.setHoraProgramada(r.getHoraProgramada());
            dto.getItems().add(item);
        });

        return ResponseEntity.ok(dto);
    }
}
