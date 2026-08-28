package com.emersondev.agendahunter.domain.repository;
import com.emersondev.agendahunter.domain.model.TareaEnfoque;
import java.util.Optional;
import java.util.UUID;

public interface TareaEnfoqueRepository {
    void guardar(TareaEnfoque tareaEnfoque);
    Optional<TareaEnfoque> buscarPorId(UUID id);
}
