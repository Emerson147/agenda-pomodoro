package com.emersondev.agendahunter.domain.repository;

import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CalendarioSiembraRepository {
    void guardar(CalendarioSiembra planificacion);
    Optional<CalendarioSiembra> buscarPorPracticanteIdYFecha(UUID practicanteId, LocalDate fecha);
    java.util.List<CalendarioSiembra> buscarPorFecha(LocalDate fecha);
}
