package com.emersondev.agendahunter.application.usecase.planificacion;

import com.emersondev.agendahunter.domain.model.CalendarioSiembra;
import com.emersondev.agendahunter.domain.repository.CalendarioSiembraRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CerrarDiasGlobalUseCase {

    private final CalendarioSiembraRepository calendarioSiembraRepository;
    private final CerrarDiaUseCase cerrarDiaUseCase;

    public void ejecutar() {
        LocalDate hoy = LocalDate.now();
        List<CalendarioSiembra> calendariosHoy = calendarioSiembraRepository.buscarPorFecha(hoy);

        for (CalendarioSiembra calendario : calendariosHoy) {
            try {
                cerrarDiaUseCase.ejecutar(calendario.getPracticanteId(), hoy);
            } catch (Exception e) {
                // Continuar con los demás
            }
        }
    }
}
