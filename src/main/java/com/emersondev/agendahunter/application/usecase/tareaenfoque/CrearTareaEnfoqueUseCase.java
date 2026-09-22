package com.emersondev.agendahunter.application.usecase.tareaenfoque;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.TareaEnfoqueRepository;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
public class CrearTareaEnfoqueUseCase {
    private final TareaEnfoqueRepository repository;

    public TareaEnfoque ejecutar(UUID practicanteId, String titulo, int pomodorosEstimados) {
        TareaEnfoque tarea = new TareaEnfoque(UUID.randomUUID(), practicanteId, titulo, false, pomodorosEstimados, new ArrayList<>(), null, null);
        repository.guardar(tarea);
        return tarea;
    }
}
