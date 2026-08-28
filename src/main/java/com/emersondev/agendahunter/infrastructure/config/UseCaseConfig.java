package com.emersondev.agendahunter.infrastructure.config;

import com.emersondev.agendahunter.application.usecase.practicante.ObtenerPerfilUseCase;
import com.emersondev.agendahunter.application.usecase.practicante.LimpiarMalezaUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.*;
import com.emersondev.agendahunter.application.usecase.recordatorio.*;
import com.emersondev.agendahunter.application.usecase.rutinadiaria.*;
import com.emersondev.agendahunter.application.usecase.tareaenfoque.*;
import com.emersondev.agendahunter.domain.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ObtenerPerfilUseCase obtenerPerfilUseCase(PracticanteRepository practicanteRepository) {
        return new ObtenerPerfilUseCase(practicanteRepository);
    }

    @Bean
    public LimpiarMalezaUseCase limpiarMalezaUseCase(PracticanteRepository practicanteRepository) {
        return new LimpiarMalezaUseCase(practicanteRepository);
    }

    // Recordatorio Use Cases
    @Bean
    public CrearRecordatorioUseCase crearRecordatorioUseCase(RecordatorioRepository repo) {
        return new CrearRecordatorioUseCase(repo);
    }

    @Bean
    public CompletarRecordatorioUseCase completarRecordatorioUseCase(RecordatorioRepository repo, PracticanteRepository practicanteRepo) {
        return new CompletarRecordatorioUseCase(repo, practicanteRepo);
    }

    // TareaEnfoque Use Cases
    @Bean
    public CrearTareaEnfoqueUseCase crearTareaEnfoqueUseCase(TareaEnfoqueRepository repo) {
        return new CrearTareaEnfoqueUseCase(repo);
    }

    @Bean
    public IniciarCicloUseCase iniciarCicloUseCase(TareaEnfoqueRepository repo) {
        return new IniciarCicloUseCase(repo);
    }

    @Bean
    public CompletarCicloActualUseCase completarCicloActualUseCase(TareaEnfoqueRepository repo, PracticanteRepository practicanteRepo) {
        return new CompletarCicloActualUseCase(repo, practicanteRepo);
    }

    // RutinaDiaria Use Cases
    @Bean
    public CrearRutinaDiariaUseCase crearRutinaDiariaUseCase(RutinaDiariaRepository repo) {
        return new CrearRutinaDiariaUseCase(repo);
    }

    @Bean
    public MarcarSerieCompletadaUseCase marcarSerieCompletadaUseCase(RutinaDiariaRepository repo) {
        return new MarcarSerieCompletadaUseCase(repo);
    }

    // Planificacion Use Cases
    @Bean
    public PlanificarRecordatorioUseCase planificarRecordatorioUseCase(
            RecordatorioRepository recordatorioRepo, 
            CalendarioSiembraRepository calendarioRepo) {
        return new PlanificarRecordatorioUseCase(recordatorioRepo, calendarioRepo);
    }

    @Bean
    public PlanificarTareaEnfoqueUseCase planificarTareaEnfoqueUseCase(
            TareaEnfoqueRepository enfoqueRepo,
            CalendarioSiembraRepository calendarioRepo,
            PracticanteRepository practicanteRepo) {
        return new PlanificarTareaEnfoqueUseCase(enfoqueRepo, calendarioRepo, practicanteRepo);
    }

    @Bean
    public PlanificarRutinaDiariaUseCase planificarRutinaDiariaUseCase(
            RutinaDiariaRepository rutinaRepo, 
            CalendarioSiembraRepository calendarioRepo) {
        return new PlanificarRutinaDiariaUseCase(rutinaRepo, calendarioRepo);
    }

    @Bean
    public CerrarDiaUseCase cerrarDiaUseCase(
            CalendarioSiembraRepository calendarioRepo, 
            PracticanteRepository practicanteRepo) {
        return new CerrarDiaUseCase(calendarioRepo, practicanteRepo);
    }

    @Bean
    public CerrarDiasGlobalUseCase cerrarDiasGlobalUseCase(
            CalendarioSiembraRepository calendarioRepo,
            CerrarDiaUseCase cerrarDiaUseCase) {
        return new CerrarDiasGlobalUseCase(calendarioRepo, cerrarDiaUseCase);
    }
}
