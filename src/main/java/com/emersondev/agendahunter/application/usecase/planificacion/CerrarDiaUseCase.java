package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.Reflexion;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CerrarDiaUseCase {

    private final CalendarioSiembraRepository calendarioSiembraRepository;

    public void ejecutar(UUID practicanteId, LocalDate fecha, List<Reflexion> reflexiones) {
        CalendarioSiembra calendario = calendarioSiembraRepository.buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseThrow(() -> new DomainException("No hay planificación para la fecha dada."));

        calendario.cerrarDia(reflexiones);
        calendarioSiembraRepository.guardar(calendario);
    }
}
