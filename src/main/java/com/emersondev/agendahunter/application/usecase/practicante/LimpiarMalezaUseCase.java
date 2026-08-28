package com.emersondev.agendahunter.application.usecase.practicante;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class LimpiarMalezaUseCase {

    private final PracticanteRepository practicanteRepository;

    public void ejecutar(UUID practicanteId) {
        Practicante practicante = practicanteRepository.buscarPorId(practicanteId)
                .orElseThrow(() -> new DomainException("No existe el practicante."));
        
        practicante.limpiarMaleza();
        practicanteRepository.guardar(practicante);
        log.info("Practicante {} limpió 1 de maleza. Armonía restante: {}", practicante.getId(), practicante.getArmonia());
    }
}
