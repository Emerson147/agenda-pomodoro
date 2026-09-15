package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.repository.TareaEnfoqueRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class PlanificarTareaEnfoqueUseCase {
    private final TareaEnfoqueRepository enfoqueRepository;
    private final CalendarioSiembraRepository calendarioSiembraRepository;

    public void ejecutar(UUID practicanteId, UUID tareaId, LocalDate fecha, String faseDia, String horaProgramada) {

        TareaEnfoque tarea = enfoqueRepository.buscarPorId(tareaId)
                .orElseThrow(() -> new DomainException("No existe."));

        tarea.setFaseDia(faseDia);
        tarea.setHoraProgramada(horaProgramada);
        enfoqueRepository.guardar(tarea);

        CalendarioSiembra planificacion = calendarioSiembraRepository
                .buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseGet(() -> new CalendarioSiembra(practicanteId, fecha));

        planificacion.agregarTareaEnfoque(tarea);
        calendarioSiembraRepository.guardar(planificacion);
    }
}
