package com.emersondev.agendahunter.infrastructure.persistence.rutinadiaria;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "rutinas_diarias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RutinaDiariaJpaEntity {
    @Id
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private int seriesTotales;
    private int seriesCompletadas;

    @jakarta.persistence.Column(name = "fase_dia")
    private String faseDia;

    @jakarta.persistence.Column(name = "hora_programada")
    private String horaProgramada;
}
