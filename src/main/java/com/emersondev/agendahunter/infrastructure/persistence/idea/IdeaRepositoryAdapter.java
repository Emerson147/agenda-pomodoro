package com.emersondev.agendahunter.infrastructure.persistence.idea;

import com.emersondev.agendahunter.domain.model.Idea;
import com.emersondev.agendahunter.domain.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class IdeaRepositoryAdapter implements IdeaRepository {

    private final SpringDataIdeaRepository jpaRepository;

    @Override
    public void guardar(Idea idea) {
        IdeaJpaEntity entity = new IdeaJpaEntity(
                idea.getId(),
                idea.getPracticanteId(),
                idea.getContenido(),
                idea.getFechaCaptura(),
                idea.isProcesada()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Idea> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Idea> listarPendientesPorPracticante(UUID practicanteId) {
        return jpaRepository.findByPracticanteIdAndProcesadaFalse(practicanteId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Idea toDomain(IdeaJpaEntity entity) {
        return new Idea(
                entity.getId(),
                entity.getPracticanteId(),
                entity.getContenido(),
                entity.getFechaCaptura(),
                entity.isProcesada()
        );
    }
}
