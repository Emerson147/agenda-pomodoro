package com.emersondev.agendahunter.infrastructure.persistence.rutinadiaria;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import com.emersondev.agendahunter.domain.repository.RutinaDiariaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RutinaDiariaRepositoryAdapter implements RutinaDiariaRepository {
    private final SpringDataRutinaDiariaRepository jpaRepository;

    @Override
    public void guardar(RutinaDiaria rutina) {
        RutinaDiariaJpaEntity entity = new RutinaDiariaJpaEntity(
            rutina.getId(),
            rutina.getPracticanteId(),
            rutina.getTitulo(),
            rutina.getSeriesTotales(),
            rutina.getSeriesCompletadas(),
            rutina.getFaseDia(),
            rutina.getHoraProgramada()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<RutinaDiaria> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(entity -> 
            new RutinaDiaria(entity.getId(), entity.getPracticanteId(), entity.getTitulo(), entity.getSeriesTotales(), entity.getSeriesCompletadas(), entity.getFaseDia(), entity.getHoraProgramada())
        );
    }
}
