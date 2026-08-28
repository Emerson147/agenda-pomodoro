package com.emersondev.agendahunter.application.usecase.practicante;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ObtenerPerfilUseCase {

    private final PracticanteRepository practicanteRepository;

    public Practicante ejecutar(UUID practicanteId) {
        log.info("Obteniendo perfil para el practicante: {}", practicanteId);
        return practicanteRepository.buscarPorId(practicanteId)
                .orElseThrow(() -> new DomainException("No existe el practicante."));
    }
}
