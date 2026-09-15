package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class CerrarDiaUseCase {

    private final CalendarioSiembraRepository calendarioSiembraRepository;

    public void ejecutar(UUID practicanteId, LocalDate fecha) {
        CalendarioSiembra calendario = calendarioSiembraRepository.buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseThrow(() -> new DomainException("No hay planificación para la fecha dada."));

        // TODO: Aquí implementaremos la lógica del Espacio de Reflexión en el futuro
    }
}
