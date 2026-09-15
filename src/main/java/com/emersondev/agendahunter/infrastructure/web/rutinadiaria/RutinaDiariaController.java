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


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearRutinaDiariaRequest req) {
        return ResponseEntity.ok(crearUseCase.ejecutar(req.getPracticanteId(), req.getTitulo(), req.getSeriesTotales()));
    }

    @PostMapping("/{id}/marcar-serie")
    public ResponseEntity<Void> marcarSerie(@PathVariable UUID id) {
        marcarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }
}
