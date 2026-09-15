package com.emersondev.agendahunter.application.usecase.idea;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.Idea;
import com.emersondev.agendahunter.domain.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class ProcesarIdeaUseCase {

    private final IdeaRepository ideaRepository;

    public void ejecutar(UUID practicanteId, UUID ideaId) {
        Idea idea = ideaRepository.buscarPorId(ideaId)
                .orElseThrow(() -> new DomainException("La idea no existe."));

        if (!idea.getPracticanteId().equals(practicanteId)) {
            throw new DomainException("No tienes permiso para procesar esta idea.");
        }

        idea.procesar();
        ideaRepository.guardar(idea);
    }
}
