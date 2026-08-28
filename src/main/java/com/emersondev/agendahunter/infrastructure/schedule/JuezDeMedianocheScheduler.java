package com.emersondev.agendahunter.infrastructure.schedule;

import com.emersondev.agendahunter.application.usecase.planificacion.CerrarDiasGlobalUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JuezDeMedianocheScheduler {

    private final CerrarDiasGlobalUseCase cerrarDiasGlobalUseCase;

    @Scheduled(cron = "0 0 0 * * ?")
    public void ejecutarCierreDiario() {
        log.info("Iniciando Juez de Medianoche para cerrar los días...");
        cerrarDiasGlobalUseCase.ejecutar();
        log.info("Juez de Medianoche finalizado.");
    }
}
