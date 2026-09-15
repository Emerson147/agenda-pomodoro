package com.emersondev.agendahunter.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CicloEnfoque {
    private UUID id;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private EstadoCiclo estado;
    private int duracionMinutos;
    private TipoCiclo tipo;

    public CicloEnfoque(int duracionMinutos, TipoCiclo tipo) {
        this.id = UUID.randomUUID();
        this.horaInicio = LocalDateTime.now();
        this.estado = EstadoCiclo.EN_CURSO;
        this.duracionMinutos = duracionMinutos;
        this.tipo = tipo != null ? tipo : TipoCiclo.ENFOQUE;
    }

    public CicloEnfoque(UUID id, LocalDateTime horaInicio, LocalDateTime horaFin, EstadoCiclo estado, int duracionMinutos, TipoCiclo tipo) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.duracionMinutos = duracionMinutos;
        this.tipo = tipo;
    }
}
