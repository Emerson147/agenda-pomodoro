package com.emersondev.agendahunter.infrastructure.persistence.rutinadiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataRutinaDiariaRepository extends JpaRepository<RutinaDiariaJpaEntity, UUID> {
}
