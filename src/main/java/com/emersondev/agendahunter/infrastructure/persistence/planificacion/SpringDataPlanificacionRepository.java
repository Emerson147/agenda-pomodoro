package com.emersondev.agendahunter.infrastructure.persistence.planificacion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPlanificacionRepository extends JpaRepository<CalendarioSiembraJpaEntity, UUID> {
    Optional<CalendarioSiembraJpaEntity> findByPracticanteIdAndFecha(UUID practicanteId, LocalDate fecha);
    java.util.List<CalendarioSiembraJpaEntity> findByFecha(LocalDate fecha);
}
