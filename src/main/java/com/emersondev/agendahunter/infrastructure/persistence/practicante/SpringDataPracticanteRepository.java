package com.emersondev.agendahunter.infrastructure.persistence.practicante;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repositorio clásico de Spring Data JPA.
 * Nos regala los métodos de base de datos genéricos (save, findById, etc).
 */
public interface SpringDataPracticanteRepository extends JpaRepository<PracticanteJpaEntity, UUID> {
    java.util.Optional<PracticanteJpaEntity> findByEmail(String email);
}
