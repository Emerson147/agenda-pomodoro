package com.emersondev.agendahunter.infrastructure.persistence.recordatorio;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "recordatorios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordatorioJpaEntity {
    @Id
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private boolean completado;

    @jakarta.persistence.Column(name = "fase_dia")
    private String faseDia;

    @jakarta.persistence.Column(name = "hora_programada")
    private String horaProgramada;
}
