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

    public static class CrearReq { public UUID practicanteId; public String titulo; }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearReq req) {
        return ResponseEntity.ok(crearUseCase.ejecutar(req.practicanteId, req.titulo));
    }

    @PostMapping("/{id}/iniciar-ciclo")
    public ResponseEntity<Void> iniciarCiclo(@PathVariable UUID id) {
        iniciarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/completar-ciclo")
    public ResponseEntity<Void> completarCiclo(@PathVariable UUID id) {
        completarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }
}
