package com.emersondev.agendahunter.application.usecase.idea;

import com.emersondev.agendahunter.domain.model.Idea;
import com.emersondev.agendahunter.domain.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CapturarIdeaUseCase {

    private final IdeaRepository ideaRepository;

    public Idea ejecutar(UUID practicanteId, String contenido) {
        Idea nuevaIdea = new Idea(practicanteId, contenido);
        ideaRepository.guardar(nuevaIdea);
        return nuevaIdea;
    }
}
