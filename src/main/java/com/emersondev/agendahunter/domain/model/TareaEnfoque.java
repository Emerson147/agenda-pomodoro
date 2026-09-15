package com.emersondev.agendahunter.domain.model;
import com.emersondev.agendahunter.domain.exception.DomainException;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class TareaEnfoque {
    private UUID id;
    private UUID practicanteId;
    private String titulo;
    private boolean completado;
    private List<CicloEnfoque> ciclos;
    private String faseDia;
    private String horaProgramada;

    public TareaEnfoque(UUID id, UUID practicanteId, String titulo, boolean completado, List<CicloEnfoque> ciclos, String faseDia, String horaProgramada) {
        this.id = id != null ? id : UUID.randomUUID();
        this.practicanteId = practicanteId;
        this.titulo = titulo;
        this.completado = completado;
        this.ciclos = ciclos != null ? ciclos : new ArrayList<>();
        this.faseDia = faseDia;
        this.horaProgramada = horaProgramada;
    }
    
    public void iniciarCiclo(int duracionMinutos, TipoCiclo tipo) {
        this.ciclos.add(new CicloEnfoque(duracionMinutos, tipo));
    }
    
    public void completarCicloActual() {
        if (this.ciclos.isEmpty()) {
            throw new DomainException("No hay ciclos iniciados.");
        }
        CicloEnfoque cicloActual = this.ciclos.get(this.ciclos.size() - 1);
        if (cicloActual.getEstado() != EstadoCiclo.EN_CURSO) {
            throw new DomainException("El ciclo actual no está en curso.");
        }
        if (LocalDateTime.now().isBefore(cicloActual.getHoraInicio().plusMinutes(cicloActual.getDuracionMinutos()))) {
            throw new DomainException("Deben pasar al menos " + cicloActual.getDuracionMinutos() + " minutos desde el inicio del ciclo.");
        }
        cicloActual.setEstado(EstadoCiclo.COMPLETADO);
        cicloActual.setHoraFin(LocalDateTime.now());
    }
}
