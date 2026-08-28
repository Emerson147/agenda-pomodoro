package com.emersondev.agendahunter.infrastructure.persistence.recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataRecordatorioRepository extends JpaRepository<RecordatorioJpaEntity, UUID> {
}
