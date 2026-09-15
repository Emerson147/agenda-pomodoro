package com.emersondev.agendahunter.infrastructure.web.idea;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapturarIdeaRequest {
    private String contenido;
}
