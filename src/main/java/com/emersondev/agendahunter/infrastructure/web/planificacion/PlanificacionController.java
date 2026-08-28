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
import java.util.stream.Collectors;
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

    public static class PlanificarReq {
        public UUID practicanteId;
        public UUID itemId;
        public LocalDate fecha;
        public String faseDia;
        public String horaProgramada;
    }

    @PostMapping("/recordatorio")
    public ResponseEntity<Void> planificarRecordatorio(@RequestBody PlanificarReq req) {
        planificarRecordatorioUseCase.ejecutar(req.practicanteId, req.itemId, req.fecha, req.faseDia, req.horaProgramada);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/enfoque")
    public ResponseEntity<Void> planificarEnfoque(@RequestBody PlanificarReq req) {
        planificarTareaEnfoqueUseCase.ejecutar(req.practicanteId, req.itemId, req.fecha, req.faseDia, req.horaProgramada);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/rutina")
    public ResponseEntity<Void> planificarRutina(@RequestBody PlanificarReq req) {
        planificarRutinaDiariaUseCase.ejecutar(req.practicanteId, req.itemId, req.fecha, req.faseDia, req.horaProgramada);
        return ResponseEntity.ok().build();
    }

    public static class CerrarDiaReq {
        public UUID practicanteId;
        public LocalDate fecha;
    }

    @PostMapping("/cerrar-dia")
    public ResponseEntity<Void> cerrarDia(@RequestBody CerrarDiaReq req) {
        cerrarDiaUseCase.ejecutar(req.practicanteId, req.fecha);
        return ResponseEntity.ok().build();
    }

    public static class AgendaItemDto {
        public UUID id;
        public String titulo;
        public String tipo; // "RECORDATORIO", "ENFOQUE", "RUTINA"
        public boolean completado;
        public String faseDia;
        public String horaProgramada;
    }

    public static class AgendaDto {
        public UUID practicanteId;
        public LocalDate fecha;
        public List<AgendaItemDto> items;
    }

    @GetMapping("/agenda/{practicanteId}/{fecha}")
    public ResponseEntity<AgendaDto> obtenerAgenda(
            @PathVariable UUID practicanteId,
            @PathVariable LocalDate fecha) {
            
        CalendarioSiembra planificacion = calendarioSiembraRepository
                .buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElse(null);

        if (planificacion == null) {
            return ResponseEntity.notFound().build();
        }

        AgendaDto dto = new AgendaDto();
        dto.practicanteId = practicanteId;
        dto.fecha = fecha;
        dto.items = new java.util.ArrayList<>();

        planificacion.getRecordatorios().forEach(r -> {
            AgendaItemDto item = new AgendaItemDto();
            item.id = r.getId();
            item.titulo = r.getTitulo();
            item.tipo = "RECORDATORIO";
            item.completado = r.isCompletado();
            item.faseDia = r.getFaseDia();
            item.horaProgramada = r.getHoraProgramada();
            dto.items.add(item);
        });

        planificacion.getTareasEnfoque().forEach(t -> {
            AgendaItemDto item = new AgendaItemDto();
            item.id = t.getId();
            item.titulo = t.getTitulo();
            item.tipo = "ENFOQUE";
            item.completado = t.isCompletado();
            item.faseDia = t.getFaseDia();
            item.horaProgramada = t.getHoraProgramada();
            dto.items.add(item);
        });

        planificacion.getRutinasDiarias().forEach(r -> {
            AgendaItemDto item = new AgendaItemDto();
            item.id = r.getId();
            item.titulo = r.getTitulo();
            item.tipo = "RUTINA";
            item.completado = (r.getSeriesCompletadas() >= r.getSeriesTotales());
            item.faseDia = r.getFaseDia();
            item.horaProgramada = r.getHoraProgramada();
            dto.items.add(item);
        });

        return ResponseEntity.ok(dto);
    }
}
