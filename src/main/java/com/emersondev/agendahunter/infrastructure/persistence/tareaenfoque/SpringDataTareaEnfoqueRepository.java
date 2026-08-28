package com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataTareaEnfoqueRepository extends JpaRepository<TareaEnfoqueJpaEntity, UUID> {
}
