package com.emersondev.agendahunter.infrastructure.persistence.tareaenfoque;
import com.emersondev.agendahunter.domain.model.EstadoCiclo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ciclos_enfoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CicloEnfoqueJpaEntity {
    @Id
    private UUID id;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    @Enumerated(EnumType.STRING)
    private EstadoCiclo estado;
    
    @Column(name = "duracion_minutos")
    private int duracionMinutos;
    
    @Enumerated(EnumType.STRING)
    private com.emersondev.agendahunter.domain.model.TipoCiclo tipo;
}
