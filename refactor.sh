#!/bin/bash
set -e

BASE_DIR="src/main/java/com/emersondev/agendahunter/infrastructure/web"

# --- TAREA ENFOQUE ---
mkdir -p $BASE_DIR/tareaenfoque
cat << 'INNER' > $BASE_DIR/tareaenfoque/CrearTareaEnfoqueRequest.java
package com.emersondev.agendahunter.infrastructure.web.tareaenfoque;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearTareaEnfoqueRequest {
    private UUID practicanteId;
    private String titulo;
}
INNER

cat << 'INNER' > $BASE_DIR/tareaenfoque/IniciarCicloRequest.java
package com.emersondev.agendahunter.infrastructure.web.tareaenfoque;
import lombok.Data;
@Data
public class IniciarCicloRequest {
    private int duracionMinutos;
    private String tipo;
}
INNER

# Fix TareaEnfoqueController
sed -i '/public static class CrearReq/d' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i '/public static class IniciarCicloReq/,/}/d' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/CrearReq/CrearTareaEnfoqueRequest/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/IniciarCicloReq/IniciarCicloRequest/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/req.practicanteId/req.getPracticanteId()/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/req.titulo/req.getTitulo()/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/req.duracionMinutos/req.getDuracionMinutos()/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java
sed -i 's/req.tipo/req.getTipo()/g' $BASE_DIR/tareaenfoque/TareaEnfoqueController.java


# --- RUTINA DIARIA ---
mkdir -p $BASE_DIR/rutinadiaria
cat << 'INNER' > $BASE_DIR/rutinadiaria/CrearRutinaDiariaRequest.java
package com.emersondev.agendahunter.infrastructure.web.rutinadiaria;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearRutinaDiariaRequest {
    private UUID practicanteId;
    private String titulo;
    private int seriesTotales;
}
INNER

# Fix RutinaDiariaController
sed -i '/public static class CrearReq/d' $BASE_DIR/rutinadiaria/RutinaDiariaController.java
sed -i 's/CrearReq/CrearRutinaDiariaRequest/g' $BASE_DIR/rutinadiaria/RutinaDiariaController.java
sed -i 's/req.practicanteId/req.getPracticanteId()/g' $BASE_DIR/rutinadiaria/RutinaDiariaController.java
sed -i 's/req.titulo/req.getTitulo()/g' $BASE_DIR/rutinadiaria/RutinaDiariaController.java
sed -i 's/req.seriesTotales/req.getSeriesTotales()/g' $BASE_DIR/rutinadiaria/RutinaDiariaController.java


# --- RECORDATORIO ---
mkdir -p $BASE_DIR/recordatorio
cat << 'INNER' > $BASE_DIR/recordatorio/CrearRecordatorioRequest.java
package com.emersondev.agendahunter.infrastructure.web.recordatorio;
import java.util.UUID;
import lombok.Data;
@Data
public class CrearRecordatorioRequest {
    private UUID practicanteId;
    private String titulo;
}
INNER

# Fix RecordatorioController
sed -i '/public static class CrearReq/d' $BASE_DIR/recordatorio/RecordatorioController.java
sed -i 's/CrearReq/CrearRecordatorioRequest/g' $BASE_DIR/recordatorio/RecordatorioController.java
sed -i 's/req.practicanteId/req.getPracticanteId()/g' $BASE_DIR/recordatorio/RecordatorioController.java
sed -i 's/req.titulo/req.getTitulo()/g' $BASE_DIR/recordatorio/RecordatorioController.java


# --- PLANIFICACION ---
mkdir -p $BASE_DIR/planificacion
cat << 'INNER' > $BASE_DIR/planificacion/PlanificarRequest.java
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
INNER

cat << 'INNER' > $BASE_DIR/planificacion/CerrarDiaRequest.java
package com.emersondev.agendahunter.infrastructure.web.planificacion;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;
@Data
public class CerrarDiaRequest {
    private UUID practicanteId;
    private LocalDate fecha;
}
INNER

cat << 'INNER' > $BASE_DIR/planificacion/AgendaItemDTO.java
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
INNER

cat << 'INNER' > $BASE_DIR/planificacion/AgendaDTO.java
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
INNER

# Replace in PlanificacionController.java (it's big, better to replace using a Java tool or carefully with sed, but let's just rewrite the whole file for safety)
cat << 'INNER' > $BASE_DIR/planificacion/PlanificacionController.java
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
INNER

