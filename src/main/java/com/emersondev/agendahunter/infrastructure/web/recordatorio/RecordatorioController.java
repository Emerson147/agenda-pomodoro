package com.emersondev.agendahunter.infrastructure.web.recordatorio;
import com.emersondev.agendahunter.application.usecase.recordatorio.CrearRecordatorioUseCase;
import com.emersondev.agendahunter.application.usecase.recordatorio.CompletarRecordatorioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recordatorios")
@RequiredArgsConstructor
public class RecordatorioController {
    private final CrearRecordatorioUseCase crearUseCase;
    private final CompletarRecordatorioUseCase completarUseCase;

    public static class CrearReq { public UUID practicanteId; public String titulo; }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CrearReq req) {
        return ResponseEntity.ok(crearUseCase.ejecutar(req.practicanteId, req.titulo));
    }

    @PostMapping("/{id}/completar")
    public ResponseEntity<Void> completar(@PathVariable UUID id) {
        completarUseCase.ejecutar(id);
        return ResponseEntity.ok().build();
    }
}
