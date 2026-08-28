package com.emersondev.agendahunter.infrastructure.persistence.planificacion;

import com.emersondev.agendahunter.infrastructure.persistence.recordatorio.RecordatorioJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.rutinadiaria.RutinaDiariaJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque.TareaEnfoqueJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "planificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarioSiembraJpaEntity {
    @Id
    private UUID id;
    
    private UUID practicanteId;
    
    private LocalDate fecha;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "planificacion_recordatorios",
            joinColumns = @JoinColumn(name = "planificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "recordatorio_id")
    )
    private List<RecordatorioJpaEntity> recordatorios;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "planificacion_tareas_enfoque",
            joinColumns = @JoinColumn(name = "planificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "tarea_enfoque_id")
    )
    private List<TareaEnfoqueJpaEntity> tareasEnfoque;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "planificacion_rutinas",
            joinColumns = @JoinColumn(name = "planificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "rutina_id")
    )
    private List<RutinaDiariaJpaEntity> rutinasDiarias;
}
