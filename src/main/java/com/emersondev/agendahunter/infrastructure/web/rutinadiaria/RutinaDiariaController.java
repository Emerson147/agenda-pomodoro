package com.emersondev.agendahunter.infrastructure.web.rutinadiaria;
import com.emersondev.agendahunter.application.usecase.rutinadiaria.CrearRutinaDiariaUseCase;
import com.emersondev.agendahunter.application.usecase.rutinadiaria.MarcarSerieCompletadaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rutinas")
@RequiredArgsConstructor
public class RutinaDiariaController {
    private final CrearRutinaDiariaUseCase crearUseCase;
    private final MarcarSerieCompletadaUseCase marcarUseCase;

    public static class CrearReq { public UUID practicanteId; public String titulo; public int seriesTotales; }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearReq req) {
        return ResponseEntity.ok(crearUseCase.ejecutar(req.practicanteId, req.titulo, req.seriesTotales));
    }

    @PostMapping("/{id}/marcar-serie")
    public ResponseEntity<Void> marcarSerie(@PathVariable UUID id) {
        marcarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }
}
