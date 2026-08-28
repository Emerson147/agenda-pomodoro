package com.emersondev.agendahunter.application.usecase.rutinadiaria;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import com.emersondev.agendahunter.domain.repository.RutinaDiariaRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class MarcarSerieCompletadaUseCase {
    private final RutinaDiariaRepository repository;

    public void ejecutar(UUID id) {
        RutinaDiaria rutina = repository.buscarPorId(id).orElseThrow(() -> new DomainException("No encontrado"));
        rutina.marcarSerieCompletada();
        repository.guardar(rutina);
    }
}
