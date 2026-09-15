package com.emersondev.agendahunter.application.usecase.idea;

import com.emersondev.agendahunter.domain.model.Idea;
import com.emersondev.agendahunter.domain.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListarIdeasPendientesUseCase {

    private final IdeaRepository ideaRepository;

    public List<Idea> ejecutar(UUID practicanteId) {
        return ideaRepository.listarPendientesPorPracticante(practicanteId);
    }
}
