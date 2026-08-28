package com.emersondev.agendahunter.application.usecase.rutinadiaria;
import com.emersondev.agendahunter.domain.model.RutinaDiaria;
import com.emersondev.agendahunter.domain.repository.RutinaDiariaRepository;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CrearRutinaDiariaUseCase {
    private final RutinaDiariaRepository repository;

    public RutinaDiaria ejecutar(UUID practicanteId, String titulo, int seriesTotales) {
        RutinaDiaria rutina = new RutinaDiaria(UUID.randomUUID(), practicanteId, titulo, seriesTotales, 0, null, null);
        repository.guardar(rutina);
        return rutina;
    }
}
