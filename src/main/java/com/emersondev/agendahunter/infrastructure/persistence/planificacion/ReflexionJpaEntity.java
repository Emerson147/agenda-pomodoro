package com.emersondev.agendahunter.infrastructure.persistence.planificacion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "reflexiones_diarias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReflexionJpaEntity {
    @Id
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendario_id", nullable = false)
    private CalendarioSiembraJpaEntity calendario;
    
    @Column(nullable = false, length = 1000)
    private String pregunta;
    
    @Column(nullable = false, length = 2000)
    private String respuesta;
}
