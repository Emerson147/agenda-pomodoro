package com.emersondev.agendahunter.infrastructure.persistence.planificacion;

import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.model.CicloEnfoque;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import com.emersondev.agendahunter.domain.model.Reflexion;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import com.emersondev.agendahunter.infrastructure.persistence.recordatorio.RecordatorioJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.rutinadiaria.RutinaDiariaJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque.CicloEnfoqueJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque.TareaEnfoqueJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PlanificacionRepositoryAdapter implements CalendarioSiembraRepository {

    private final SpringDataPlanificacionRepository jpaRepository;

    @Override
    public void guardar(CalendarioSiembra planificacion) {
        CalendarioSiembraJpaEntity entity = jpaRepository.findById(planificacion.getId())
                .orElseGet(CalendarioSiembraJpaEntity::new);

        entity.setId(planificacion.getId());
        entity.setPracticanteId(planificacion.getPracticanteId());
        entity.setFecha(planificacion.getFecha());
        entity.setDiaCerrado(planificacion.isDiaCerrado());

        List<RecordatorioJpaEntity> recordatoriosJpa = planificacion.getRecordatorios().stream()
            .map(r -> new RecordatorioJpaEntity(r.getId(), r.getPracticanteId(), r.getTitulo(), r.isCompletado(), r.getFaseDia(), r.getHoraProgramada()))
            .collect(Collectors.toList());
        entity.setRecordatorios(recordatoriosJpa);
            
        List<TareaEnfoqueJpaEntity> enfoquesJpa = planificacion.getTareasEnfoque().stream()
            .map(t -> new TareaEnfoqueJpaEntity(t.getId(), t.getPracticanteId(), t.getTitulo(), t.isCompletado(), t.getPomodorosEstimados(),
                t.getCiclos().stream().map(c -> new CicloEnfoqueJpaEntity(c.getId(), c.getHoraInicio(), c.getHoraFin(), c.getEstado(), c.getDuracionMinutos(), c.getTipo())).collect(Collectors.toList()),
                t.getFaseDia(), t.getHoraProgramada()
            )).collect(Collectors.toList());
        entity.setTareasEnfoque(enfoquesJpa);
            
        List<RutinaDiariaJpaEntity> rutinasJpa = planificacion.getRutinasDiarias().stream()
            .map(r -> new RutinaDiariaJpaEntity(r.getId(), r.getPracticanteId(), r.getTitulo(), r.getSeriesTotales(), r.getSeriesCompletadas(), r.getFaseDia(), r.getHoraProgramada()))
            .collect(Collectors.toList());
        entity.setRutinasDiarias(rutinasJpa);
        
        List<ReflexionJpaEntity> reflexionesJpa = planificacion.getReflexiones().stream()
            .map(r -> new ReflexionJpaEntity(r.getId(), entity, r.getPregunta(), r.getRespuesta()))
            .collect(Collectors.toList());
        
        if (entity.getReflexiones() == null) {
            entity.setReflexiones(reflexionesJpa);
        } else {
            entity.getReflexiones().clear();
            entity.getReflexiones().addAll(reflexionesJpa);
        }

        jpaRepository.save(entity);
    }

    @Override
    public Optional<CalendarioSiembra> buscarPorPracticanteIdYFecha(UUID practicanteId, LocalDate fecha) {
        return jpaRepository.findByPracticanteIdAndFecha(practicanteId, fecha)
                .map(this::toDomain);
    }

    @Override
    public List<CalendarioSiembra> buscarPorFecha(LocalDate fecha) {
        return jpaRepository.findByFecha(fecha).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CalendarioSiembra toDomain(CalendarioSiembraJpaEntity entity) {
        List<Recordatorio> recordatorios = entity.getRecordatorios().stream()
            .map(r -> new Recordatorio(r.getId(), r.getPracticanteId(), r.getTitulo(), r.isCompletado(), r.getFaseDia(), r.getHoraProgramada()))
            .collect(Collectors.toList());
            
        List<TareaEnfoque> enfoques = entity.getTareasEnfoque().stream()
            .map(t -> {
                List<CicloEnfoque> ciclos = t.getCiclos().stream().map(c -> {
                    CicloEnfoque ciclo = new CicloEnfoque(c.getId(), c.getHoraInicio(), c.getHoraFin(), c.getEstado(), c.getDuracionMinutos(), c.getTipo());
                    return ciclo;
                }).collect(Collectors.toList());
                return new TareaEnfoque(t.getId(), t.getPracticanteId(), t.getTitulo(), t.isCompletado(), t.getPomodorosEstimados(), ciclos, t.getFaseDia(), t.getHoraProgramada());
            }).collect(Collectors.toList());
            
        List<RutinaDiaria> rutinas = entity.getRutinasDiarias().stream()
            .map(r -> new RutinaDiaria(r.getId(), r.getPracticanteId(), r.getTitulo(), r.getSeriesTotales(), r.getSeriesCompletadas(), r.getFaseDia(), r.getHoraProgramada()))
            .collect(Collectors.toList());
            
        List<Reflexion> reflexiones = (entity.getReflexiones() != null) ? entity.getReflexiones().stream()
            .map(r -> new Reflexion(r.getId(), r.getPregunta(), r.getRespuesta()))
            .collect(Collectors.toList()) : List.of();

        return new CalendarioSiembra(
                entity.getId(),
                entity.getPracticanteId(),
                entity.getFecha(),
                recordatorios,
                enfoques,
                rutinas,
                reflexiones,
                entity.isDiaCerrado()
        );
    }
}
