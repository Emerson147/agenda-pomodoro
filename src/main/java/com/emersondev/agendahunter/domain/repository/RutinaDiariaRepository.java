package com.emersondev.agendahunter.domain.repository;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import java.util.Optional;
import java.util.UUID;

public interface RutinaDiariaRepository {
    void guardar(RutinaDiaria rutina);
    Optional<RutinaDiaria> buscarPorId(UUID id);
}
