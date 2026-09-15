package com.emersondev.agendahunter.infrastructure.config;

import com.emersondev.agendahunter.application.usecase.idea.*;
import com.emersondev.agendahunter.application.usecase.practicante.ObtenerPerfilUseCase;
import com.emersondev.agendahunter.application.usecase.planificacion.*;
import com.emersondev.agendahunter.application.usecase.recordatorio.*;
import com.emersondev.agendahunter.application.usecase.rutinadiaria.*;
import com.emersondev.agendahunter.application.usecase.tareaenfoque.*;
import com.emersondev.agendahunter.domain.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    // Idea Use Cases
    @Bean
    public CapturarIdeaUseCase capturarIdeaUseCase(IdeaRepository ideaRepository) {
        return new CapturarIdeaUseCase(ideaRepository);
    }

    @Bean
    public ListarIdeasPendientesUseCase listarIdeasPendientesUseCase(IdeaRepository ideaRepository) {
        return new ListarIdeasPendientesUseCase(ideaRepository);
    }

    @Bean
    public ProcesarIdeaUseCase procesarIdeaUseCase(IdeaRepository ideaRepository) {
        return new ProcesarIdeaUseCase(ideaRepository);
    }

    @Bean
    public ObtenerPerfilUseCase obtenerPerfilUseCase(PracticanteRepository practicanteRepository) {
        return new ObtenerPerfilUseCase(practicanteRepository);
    }

    // Recordatorio Use Cases
    @Bean
    public CrearRecordatorioUseCase crearRecordatorioUseCase(RecordatorioRepository repo) {
        return new CrearRecordatorioUseCase(repo);
    }

    @Bean
    public CompletarRecordatorioUseCase completarRecordatorioUseCase(RecordatorioRepository repo) {
        return new CompletarRecordatorioUseCase(repo);
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
    public CompletarCicloActualUseCase completarCicloActualUseCase(TareaEnfoqueRepository repo) {
        return new CompletarCicloActualUseCase(repo);
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
            CalendarioSiembraRepository calendarioRepo) {
        return new PlanificarTareaEnfoqueUseCase(enfoqueRepo, calendarioRepo);
    }

    @Bean
    public PlanificarRutinaDiariaUseCase planificarRutinaDiariaUseCase(
            RutinaDiariaRepository rutinaRepo, 
            CalendarioSiembraRepository calendarioRepo) {
        return new PlanificarRutinaDiariaUseCase(rutinaRepo, calendarioRepo);
    }

    @Bean
    public CerrarDiaUseCase cerrarDiaUseCase(
            CalendarioSiembraRepository calendarioRepo) {
        return new CerrarDiaUseCase(calendarioRepo);
    }

    @Bean
    public CerrarDiasGlobalUseCase cerrarDiasGlobalUseCase(
            CalendarioSiembraRepository calendarioRepo,
            CerrarDiaUseCase cerrarDiaUseCase) {
        return new CerrarDiasGlobalUseCase(calendarioRepo, cerrarDiaUseCase);
    }
}
