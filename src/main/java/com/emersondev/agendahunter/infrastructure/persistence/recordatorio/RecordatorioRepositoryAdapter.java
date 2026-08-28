package com.emersondev.agendahunter.infrastructure.persistence.recordatorio;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RecordatorioRepositoryAdapter implements RecordatorioRepository {
    private final SpringDataRecordatorioRepository jpaRepository;

    @Override
    public void guardar(Recordatorio recordatorio) {
        RecordatorioJpaEntity entity = new RecordatorioJpaEntity(
            recordatorio.getId(),
            recordatorio.getPracticanteId(),
            recordatorio.getTitulo(),
            recordatorio.isCompletado(),
            recordatorio.getFaseDia(),
            recordatorio.getHoraProgramada()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Recordatorio> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(entity -> 
            new Recordatorio(entity.getId(), entity.getPracticanteId(), entity.getTitulo(), entity.isCompletado(), entity.getFaseDia(), entity.getHoraProgramada())
        );
    }
}
