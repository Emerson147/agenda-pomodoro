package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.repository.RutinaDiariaRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class PlanificarRutinaDiariaUseCase {
    private final RutinaDiariaRepository rutinaRepository;
    private final CalendarioSiembraRepository calendarioSiembraRepository;

    public void ejecutar(UUID practicanteId, UUID rutinaId, LocalDate fecha, String faseDia, String horaProgramada) {
        RutinaDiaria rutina = rutinaRepository.buscarPorId(rutinaId)
                .orElseThrow(() -> new DomainException("No existe."));

        rutina.setFaseDia(faseDia);
        rutina.setHoraProgramada(horaProgramada);
        rutinaRepository.guardar(rutina);

        CalendarioSiembra planificacion = calendarioSiembraRepository
                .buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseGet(() -> new CalendarioSiembra(practicanteId, fecha));

        planificacion.agregarRutinaDiaria(rutina);
        calendarioSiembraRepository.guardar(planificacion);
    }
}
