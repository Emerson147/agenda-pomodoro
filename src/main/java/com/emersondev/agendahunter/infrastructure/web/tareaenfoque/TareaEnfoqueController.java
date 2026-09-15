package com.emersondev.agendahunter.infrastructure.web.tareaenfoque;
import com.emersondev.agendahunter.application.usecase.tareaenfoque.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tareas-enfoque")
@RequiredArgsConstructor
public class TareaEnfoqueController {
    private final CrearTareaEnfoqueUseCase crearUseCase;
    private final IniciarCicloUseCase iniciarUseCase;
    private final CompletarCicloActualUseCase completarUseCase;


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearTareaEnfoqueRequest req) {
        return ResponseEntity.ok(crearUseCase.ejecutar(req.getPracticanteId(), req.getTitulo()));
    }


    @PostMapping("/{id}/iniciar-ciclo")
    public ResponseEntity<Void> iniciarCiclo(@PathVariable UUID id, @RequestBody(required = false) IniciarCicloRequest req) {
        int duracion = (req != null && req.getDuracionMinutos() > 0) ? req.getDuracionMinutos() : 25;
        com.emersondev.agendahunter.domain.model.TipoCiclo tipoEnum = com.emersondev.agendahunter.domain.model.TipoCiclo.ENFOQUE;
        if (req != null && req.getTipo() != null) {
            try {
                tipoEnum = com.emersondev.agendahunter.domain.model.TipoCiclo.valueOf(req.getTipo().toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }
        
        iniciarUseCase.ejecutar(id, duracion, tipoEnum);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/completar-ciclo")
    public ResponseEntity<Void> completarCiclo(@PathVariable UUID id) {
        completarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }
}
