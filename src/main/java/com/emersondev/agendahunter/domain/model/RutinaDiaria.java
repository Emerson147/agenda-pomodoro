package com.emersondev.agendahunter.domain.model;
import com.emersondev.agendahunter.domain.exception.DomainException;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class RutinaDiaria {
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private int seriesTotales;
    private int seriesCompletadas;
    private String faseDia;
    private String horaProgramada;

    public RutinaDiaria(UUID id, UUID practicanteId, String titulo, int seriesTotales, int seriesCompletadas, String faseDia, String horaProgramada) {
        this.id = id != null ? id : UUID.randomUUID();
        this.practicanteId = practicanteId;
        this.titulo = titulo;
        this.seriesTotales = seriesTotales;
        this.seriesCompletadas = seriesCompletadas;
        this.faseDia = faseDia;
        this.horaProgramada = horaProgramada;
    }
    
    public void marcarSerieCompletada() {
        if (this.seriesCompletadas >= this.seriesTotales) {
            throw new DomainException("Ya has completado todas las series de esta rutina.");
        }
        this.seriesCompletadas++;
    }
}
