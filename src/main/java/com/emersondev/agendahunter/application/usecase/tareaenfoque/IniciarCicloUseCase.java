package com.emersondev.agendahunter.application.usecase.tareaenfoque;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import com.emersondev.agendahunter.domain.repository.TareaEnfoqueRepository;
import lombok.RequiredArgsConstructor;
import com.emersondev.agendahunter.domain.model.TipoCiclo;
import java.util.UUID;

@RequiredArgsConstructor
public class IniciarCicloUseCase {
    private final TareaEnfoqueRepository repository;

    public void ejecutar(UUID id, int duracionMinutos, TipoCiclo tipo) {
        TareaEnfoque tarea = repository.buscarPorId(id).orElseThrow(() -> new DomainException("No encontrado"));
        tarea.iniciarCiclo(duracionMinutos, tipo);
        repository.guardar(tarea);
    }
}
