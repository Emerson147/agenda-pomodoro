package com.emersondev.agendahunter.infrastructure.web.planificacion;

import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarRecordatorioUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarTareaEnfoqueUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.PlanificarRutinaDiariaUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.CerrarDiaUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.ObtenerPreguntasCierreUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.Reflexion;

@RestController
@RequestMapping("/api/v1/planificacion")
@RequiredArgsConstructor
public class PlanificacionController {
    private final PlanificarRecordatorioUseCase planificarRecordatorioUseCase;
    private final PlanificarTareaEnfoqueUseCase planificarTareaEnfoqueUseCase;
    private final PlanificarRutinaDiariaUseCase planificarRutinaDiariaUseCase;
    private final CerrarDiaUseCase cerrarDiaUseCase;
    private final ObtenerPreguntasCierreUseCase obtenerPreguntasCierreUseCase;
    private final CalendarioSiembraRepository calendarioSiembraRepository;

    @GetMapping("/preguntas-cierre")
    public ResponseEntity<List<String>> obtenerPreguntasCierre() {
        return ResponseEntity.ok(obtenerPreguntasCierreUseCase.ejecutar());
    }

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
        List<Reflexion> reflexiones = req.getReflexiones() != null ? 
            req.getReflexiones().stream()
                .map(dto -> new Reflexion(dto.getPregunta(), dto.getRespuesta()))
                .collect(Collectors.toList()) 
            : new ArrayList<>();
            
        cerrarDiaUseCase.ejecutar(req.getPracticanteId(), req.getFecha(), reflexiones);
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
            item.setPomodorosEstimados(t.getPomodorosEstimados());
            
            long reales = t.getCiclos().stream()
                .filter(c -> c.getTipo() == com.emersondev.agendahunter.domain.model.TipoCiclo.ENFOQUE && 
                             c.getEstado() == com.emersondev.agendahunter.domain.model.EstadoCiclo.COMPLETADO)
                .count();
            item.setPomodorosReales((int) reales);
            
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
