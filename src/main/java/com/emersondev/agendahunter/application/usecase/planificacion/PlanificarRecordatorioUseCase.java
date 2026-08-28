package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class PlanificarRecordatorioUseCase {
    private final RecordatorioRepository recordatorioRepository;
    private final CalendarioSiembraRepository calendarioSiembraRepository;

    public void ejecutar(UUID practicanteId, UUID recordatorioId, LocalDate fecha, String faseDia, String horaProgramada) {
        Recordatorio recordatorio = recordatorioRepository.buscarPorId(recordatorioId)
                .orElseThrow(() -> new DomainException("No existe."));

        recordatorio.setFaseDia(faseDia);
        recordatorio.setHoraProgramada(horaProgramada);
        recordatorioRepository.guardar(recordatorio);

        CalendarioSiembra planificacion = calendarioSiembraRepository
                .buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseGet(() -> new CalendarioSiembra(practicanteId, fecha));

        planificacion.agregarRecordatorio(recordatorio);
        calendarioSiembraRepository.guardar(planificacion);
    }
}
