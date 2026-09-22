package com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tareas_enfoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TareaEnfoqueJpaEntity {
    @Id
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private boolean completado;
    
    @Column(name = "pomodoros_estimados", nullable = false, columnDefinition = "integer default 1")
    private int pomodorosEstimados = 1;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "tarea_enfoque_id")
    private List<CicloEnfoqueJpaEntity> ciclos;

    @Column(name = "fase_dia")
    private String faseDia;

    @Column(name = "hora_programada")
    private String horaProgramada;
}
