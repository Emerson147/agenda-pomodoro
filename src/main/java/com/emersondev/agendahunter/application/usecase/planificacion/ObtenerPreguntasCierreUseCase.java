package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.service.GeneradorPreguntasDiario;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class ObtenerPreguntasCierreUseCase {

    private final GeneradorPreguntasDiario generadorPreguntasDiario;

    public List<String> ejecutar() {
        return generadorPreguntasDiario.generarPreguntas();
    }
}
