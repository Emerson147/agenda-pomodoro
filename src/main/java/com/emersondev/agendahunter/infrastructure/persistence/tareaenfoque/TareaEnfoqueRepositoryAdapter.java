package com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque;
import com.emersondev.agendahunter.domain.model.CicloEnfoque;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.TareaEnfoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TareaEnfoqueRepositoryAdapter implements TareaEnfoqueRepository {
    private final SpringDataTareaEnfoqueRepository jpaRepository;

    @Override
    public void guardar(TareaEnfoque tarea) {
        TareaEnfoqueJpaEntity entity = new TareaEnfoqueJpaEntity(
            tarea.getId(),
            tarea.getPracticanteId(),
            tarea.getTitulo(),
            tarea.isCompletado(),
            tarea.getCiclos().stream().map(c -> new CicloEnfoqueJpaEntity(
                c.getId(), c.getHoraInicio(), c.getHoraFin(), c.getEstado()
            )).collect(Collectors.toList()),
            tarea.getFaseDia(),
            tarea.getHoraProgramada()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<TareaEnfoque> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(entity -> {
            var ciclos = entity.getCiclos().stream().map(c -> {
                CicloEnfoque ciclo = new CicloEnfoque();
                ciclo.setId(c.getId());
                ciclo.setHoraInicio(c.getHoraInicio());
                ciclo.setHoraFin(c.getHoraFin());
                ciclo.setEstado(c.getEstado());
                return ciclo;
            }).collect(Collectors.toList());
            return new TareaEnfoque(entity.getId(), entity.getPracticanteId(), entity.getTitulo(), entity.isCompletado(), ciclos, entity.getFaseDia(), entity.getHoraProgramada());
        });
    }
}
