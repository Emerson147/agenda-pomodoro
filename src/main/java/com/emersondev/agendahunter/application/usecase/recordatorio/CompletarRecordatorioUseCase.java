package com.emersondev.agendahunter.application.usecase.recordatorio;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import com.emersondev.agendahunter.domain.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CompletarRecordatorioUseCase {
    private final RecordatorioRepository repository;
    private final PracticanteRepository practicanteRepository;

    public void ejecutar(UUID id) {
        Recordatorio recordatorio = repository.buscarPorId(id).orElseThrow(() -> new DomainException("No encontrado"));
        recordatorio.completar();
        repository.guardar(recordatorio);

        Practicante practicante = practicanteRepository.buscarPorId(recordatorio.getPracticanteId())
                .orElseThrow(() -> new DomainException("Practicante no encontrado"));
        practicante.ganarArmonia(5);
        practicanteRepository.guardar(practicante);
    }
}
