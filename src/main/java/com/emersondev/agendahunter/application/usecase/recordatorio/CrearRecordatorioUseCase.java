package com.emersondev.agendahunter.application.usecase.recordatorio;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CrearRecordatorioUseCase {
    private final RecordatorioRepository repository;

    public Recordatorio ejecutar(UUID practicanteId, String titulo) {
        Recordatorio recordatorio = new Recordatorio(UUID.randomUUID(), practicanteId, titulo, false, null, null);
        repository.guardar(recordatorio);
        return recordatorio;
    }
}
