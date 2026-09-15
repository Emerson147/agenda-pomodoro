package com.emersondev.agendahunter.infrastructure.persistence.practicante;

import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de Salida.
 * Implementa el contrato dictado por el Dominio y traduce entre el mundo
 * puro de Java y el mundo de Spring/BBDD.
 */
@Repository
@RequiredArgsConstructor
public class PracticanteRepositoryAdapter implements PracticanteRepository {

    private final SpringDataPracticanteRepository jpaRepository;

    @Override
    public void guardar(Practicante practicante) {
        // 1. Traducir del Dominio -> JPA Entity
        PracticanteJpaEntity entity = new PracticanteJpaEntity(
                practicante.getId(),
                practicante.getNombre(),
                practicante.getEmail(),
                practicante.getPassword(),
                practicante.getZonaHoraria()
        );

        // 2. Guardar físicamente en BD
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Practicante> buscarPorId(UUID id) {
        // 1. Buscar físicamente en BD
        return jpaRepository.findById(id)
                // 2. Traducir de JPA Entity -> Dominio
                .map(entity -> new Practicante(
                        entity.getId(),
                        entity.getNombre(),
                        entity.getEmail(),
                        entity.getPassword(),
                        entity.getZonaHoraria()
                ));
    }

    @Override
    public Optional<Practicante> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(entity -> new Practicante(
                        entity.getId(),
                        entity.getNombre(),
                        entity.getEmail(),
                        entity.getPassword(),
                        entity.getZonaHoraria()
                ));
    }
}
