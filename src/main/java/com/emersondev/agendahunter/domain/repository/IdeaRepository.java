package com.emersondev.agendahunter.domain.repository;

import com.emersondev.agendahunter.domain.model.Idea;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdeaRepository {
    void guardar(Idea idea);
    Optional<Idea> buscarPorId(UUID id);
    List<Idea> listarPendientesPorPracticante(UUID practicanteId);
}
