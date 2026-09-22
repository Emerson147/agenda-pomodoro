package com.emersondev.agendahunter.domain.model;

import com.emersondev.agendahunter.domain.exception.DomainException;
import lombok.Getter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class CalendarioSiembra {
    private UUID id;
    private UUID practicanteId;
    private LocalDate fecha;
    private List<Recordatorio> recordatorios;
    private List<TareaEnfoque> tareasEnfoque;
    private List<RutinaDiaria> rutinasDiarias;
    private List<Reflexion> reflexiones;
    private boolean diaCerrado;

    public CalendarioSiembra(UUID practicanteId, LocalDate fecha) {
        if (practicanteId == null) throw new IllegalArgumentException("El practicanteId es obligatorio");
        if (fecha == null) throw new IllegalArgumentException("La fecha es obligatoria");

        this.id = UUID.randomUUID();
        this.practicanteId = practicanteId;
        this.fecha = fecha;
        this.recordatorios = new ArrayList<>();
        this.tareasEnfoque = new ArrayList<>();
        this.rutinasDiarias = new ArrayList<>();
        this.reflexiones = new ArrayList<>();
        this.diaCerrado = false;
    }

    public CalendarioSiembra(UUID id, UUID practicanteId, LocalDate fecha, 
                             List<Recordatorio> recordatorios, 
                             List<TareaEnfoque> tareasEnfoque, 
                             List<RutinaDiaria> rutinasDiarias,
                             List<Reflexion> reflexiones,
                             boolean diaCerrado) {
        this.id = id;
        this.practicanteId = practicanteId;
        this.fecha = fecha;
        this.recordatorios = recordatorios != null ? recordatorios : new ArrayList<>();
        this.tareasEnfoque = tareasEnfoque != null ? tareasEnfoque : new ArrayList<>();
        this.rutinasDiarias = rutinasDiarias != null ? rutinasDiarias : new ArrayList<>();
        this.reflexiones = reflexiones != null ? reflexiones : new ArrayList<>();
        this.diaCerrado = diaCerrado;
    }

    public static CalendarioSiembra crear(UUID practicanteId, LocalDate fecha) {
        return new CalendarioSiembra(UUID.randomUUID(), practicanteId, fecha, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        if (!this.practicanteId.equals(recordatorio.getPracticanteId())) {
            throw new DomainException("El recordatorio no pertenece a este practicante.");
        }
        if (this.recordatorios.size() + this.tareasEnfoque.size() >= 5) {
            throw new DomainException("Regla Anti-Burnout: Límite de 5 tareas diarias alcanzado. Protege tu ancho de banda mental.");
        }
        this.recordatorios.add(recordatorio);
    }
    
    public void agregarTareaEnfoque(TareaEnfoque tarea) {
        if (!this.practicanteId.equals(tarea.getPracticanteId())) {
            throw new DomainException("La tarea de enfoque no pertenece a este practicante.");
        }
        if (this.recordatorios.size() + this.tareasEnfoque.size() >= 5) {
            throw new DomainException("Regla Anti-Burnout: Límite de 5 tareas diarias alcanzado. Protege tu ancho de banda mental.");
        }
        if (this.tareasEnfoque.size() >= 2) {
            throw new DomainException("Regla Anti-Burnout: Solo puedes tener 2 tareas de enfoque por día. Tareas titánicas en exceso causan burnout.");
        }
        this.tareasEnfoque.add(tarea);
    }
    
    public void agregarRutinaDiaria(RutinaDiaria rutina) {
        if (!this.practicanteId.equals(rutina.getPracticanteId())) {
            throw new DomainException("La rutina no pertenece a este practicante.");
        }
        this.rutinasDiarias.add(rutina);
    }

    public void cerrarDia(List<Reflexion> reflexiones) {
        if (this.diaCerrado) {
            throw new DomainException("Este día ya se encuentra cerrado.");
        }
        if (reflexiones != null) {
            this.reflexiones.addAll(reflexiones);
        }
        this.diaCerrado = true;
    }
}
