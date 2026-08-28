package com.emersondev.agendahunter.domain.repository;

import com.emersondev.agendahunter.domain.model.Practicante;
import java.util.Optional;
import java.util.UUID;

public interface PracticanteRepository {
    void guardar(Practicante practicante);
    Optional<Practicante> buscarPorId(UUID id);
    Optional<Practicante> buscarPorEmail(String email);
}
