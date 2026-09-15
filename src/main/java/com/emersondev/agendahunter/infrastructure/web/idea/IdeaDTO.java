package com.emersondev.agendahunter.infrastructure.web.idea;

import com.emersondev.agendahunter.domain.model.Idea;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class IdeaDTO {
    private UUID id;
    private String contenido;
    private LocalDateTime fechaCaptura;

    public static IdeaDTO from(Idea idea) {
        return IdeaDTO.builder()
                .id(idea.getId())
                .contenido(idea.getContenido())
                .fechaCaptura(idea.getFechaCaptura())
                .build();
    }
}
