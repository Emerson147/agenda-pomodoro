package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class CerrarDiaUseCase {

    private final CalendarioSiembraRepository calendarioSiembraRepository;
    private final PracticanteRepository practicanteRepository;

    public void ejecutar(UUID practicanteId, LocalDate fecha) {
        CalendarioSiembra calendario = calendarioSiembraRepository.buscarPorPracticanteIdYFecha(practicanteId, fecha)
                .orElseThrow(() -> new DomainException("No hay planificación para la fecha dada."));

        boolean algunaIncompleta = calendario.getRutinasDiarias().stream()
                .anyMatch(rutina -> rutina.getSeriesCompletadas() < rutina.getSeriesTotales());

        if (algunaIncompleta) {
            Practicante practicante = practicanteRepository.buscarPorId(practicanteId)
                    .orElseThrow(() -> new DomainException("No existe el practicante."));
            practicante.agregarMaleza();
            practicanteRepository.guardar(practicante);
        } else {
            Practicante practicante = practicanteRepository.buscarPorId(practicanteId)
                    .orElseThrow(() -> new DomainException("No existe el practicante."));
            practicante.incrementarRacha();
            practicante.ganarExperiencia(50);
            practicanteRepository.guardar(practicante);
        }
    }
}
