package com.emersondev.agendahunter.domain.repository;
import com.emersondev.agendahunter.domain.model.Recordatorio;
import java.util.Optional;
import java.util.UUID;

public interface RecordatorioRepository {
    void guardar(Recordatorio recordatorio);
    Optional<Recordatorio> buscarPorId(UUID id);
}
