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
    private int nivel;
    private int experiencia;
    private int racha;
    private int armonia;
    private int maleza;
    private String zonaHoraria;

    public static PracticanteDTO from(Practicante practicante) {
        return PracticanteDTO.builder()
                .id(practicante.getId())
                .nombre(practicante.getNombre())
                .email(practicante.getEmail())
                .nivel(practicante.getNivel())
                .experiencia(practicante.getExperiencia())
                .racha(practicante.getRacha())
                .armonia(practicante.getArmonia())
                .maleza(practicante.getMaleza())
                .zonaHoraria(practicante.getZonaHoraria())
                .build();
    }
}
