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

    public CicloEnfoque() {
        this.id = UUID.randomUUID();
        this.horaInicio = LocalDateTime.now();
        this.estado = EstadoCiclo.EN_CURSO;
    }
}
