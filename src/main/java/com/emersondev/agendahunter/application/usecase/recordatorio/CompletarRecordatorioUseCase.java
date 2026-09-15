package com.emersondev.agendahunter.application.usecase.recordatorio;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CompletarRecordatorioUseCase {
    private final RecordatorioRepository repository;

    public void ejecutar(UUID id) {
        Recordatorio recordatorio = repository.buscarPorId(id).orElseThrow(() -> new DomainException("No encontrado"));
        recordatorio.completar();
        repository.guardar(recordatorio);
    }
}
