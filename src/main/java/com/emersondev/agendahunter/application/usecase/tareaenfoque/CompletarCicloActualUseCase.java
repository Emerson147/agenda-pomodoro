package com.emersondev.agendahunter.application.usecase.tareaenfoque;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import com.emersondev.agendahunter.domain.repository.TareaEnfoqueRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CompletarCicloActualUseCase {
    private final TareaEnfoqueRepository repository;
    private final PracticanteRepository practicanteRepository;

    public void ejecutar(UUID id) {
        TareaEnfoque tarea = repository.buscarPorId(id).orElseThrow(() -> new DomainException("No encontrado"));
        tarea.completarCicloActual();
        repository.guardar(tarea);

        Practicante practicante = practicanteRepository.buscarPorId(tarea.getPracticanteId())
                .orElseThrow(() -> new DomainException("Practicante no encontrado"));
        practicante.ganarArmonia(25);
        practicante.ganarExperiencia(25);
        practicanteRepository.guardar(practicante);
    }
}
