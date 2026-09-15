package com.emersondev.agendahunter.infrastructure.web.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.emersondev.agendahunter.domain.model.Practicante;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PracticanteDTO {
    private UUID id;
    private String nombre;
    private String email;
    private String zonaHoraria;

    public static PracticanteDTO from(Practicante practicante) {
        return PracticanteDTO.builder()
                .id(practicante.getId())
                .nombre(practicante.getNombre())
                .email(practicante.getEmail())
                .zonaHoraria(practicante.getZonaHoraria())
                .build();
    }
}
