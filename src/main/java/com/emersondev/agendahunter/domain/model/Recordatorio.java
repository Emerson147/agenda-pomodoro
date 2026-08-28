package com.emersondev.agendahunter.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class Recordatorio {
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private boolean completado;
    private String faseDia;
    private String horaProgramada;

    public Recordatorio(UUID id, UUID practicanteId, String titulo, boolean completado, String faseDia, String horaProgramada) {
        this.id = id != null ? id : UUID.randomUUID();
        this.practicanteId = practicanteId;
        this.titulo = titulo;
        this.completado = completado;
        this.faseDia = faseDia;
        this.horaProgramada = horaProgramada;
    }
    
    public void completar() {
        this.completado = true;
    }
}
