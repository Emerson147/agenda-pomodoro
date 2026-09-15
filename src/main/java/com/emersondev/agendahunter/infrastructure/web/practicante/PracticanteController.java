package com.emersondev.agendahunter.infrastructure.web.practicante;

import com.emersondev.agendahunter.application.usecase.practicante.ObtenerPerfilUseCase;
import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.infrastructure.web.auth.PracticanteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emersondev.agendahunter.infrastructure.config.security.PracticanteUserDetails;
import org.springframework.security.core.Authentication;

import java.util.UUID;

/**
 * Adaptador de Entrada (Web).
 * Recibe peticiones HTTP (JSON), las valida, y se las pasa al Caso de Uso.
 * No tiene ni un solo `if` de negocio.
 */
@Slf4j
@RestController
@RequestMapping("/api/practicantes")
@RequiredArgsConstructor
public class PracticanteController {

    private final ObtenerPerfilUseCase obtenerPerfilUseCase;

    @GetMapping("/me")
    public ResponseEntity<PracticanteDTO> obtenerMiPerfil(Authentication authentication) {
        UUID practicanteId = extraerId(authentication);
        log.info("Obteniendo perfil para practicante: {}", practicanteId);
        
        Practicante practicante = obtenerPerfilUseCase.ejecutar(practicanteId);
        
        return ResponseEntity.ok(PracticanteDTO.from(practicante));
    }
    
    private UUID extraerId(Authentication authentication) {
        PracticanteUserDetails userDetails = (PracticanteUserDetails) authentication.getPrincipal();
        return userDetails.getPracticante().getId();
    }
}
