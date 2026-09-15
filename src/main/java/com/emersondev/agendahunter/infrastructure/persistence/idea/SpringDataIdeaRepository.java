package com.emersondev.agendahunter.infrastructure.persistence.idea;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface SpringDataIdeaRepository extends JpaRepository<IdeaJpaEntity, UUID> {
    List<IdeaJpaEntity> findByPracticanteIdAndProcesadaFalse(UUID practicanteId);
}
