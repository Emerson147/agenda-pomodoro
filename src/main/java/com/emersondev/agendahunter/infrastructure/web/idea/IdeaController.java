package com.emersondev.agendahunter.infrastructure.web.idea;

import com.emersondev.agendahunter.application.usecase.idea.CapturarIdeaUseCase;
import com.emersondev.agendahunter.application.usecase.idea.ListarIdeasPendientesUseCase;
import com.emersondev.agendahunter.application.usecase.idea.ProcesarIdeaUseCase;
import com.emersondev.agendahunter.domain.model.Idea;
import com.emersondev.agendahunter.infrastructure.config.security.PracticanteUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
public class IdeaController {

    private final CapturarIdeaUseCase capturarIdeaUseCase;
    private final ListarIdeasPendientesUseCase listarIdeasPendientesUseCase;
    private final ProcesarIdeaUseCase procesarIdeaUseCase;

    @PostMapping
    public ResponseEntity<IdeaDTO> capturarIdea(@RequestBody CapturarIdeaRequest request, Authentication authentication) {
        UUID practicanteId = extraerId(authentication);
        Idea nuevaIdea = capturarIdeaUseCase.ejecutar(practicanteId, request.getContenido());
        return ResponseEntity.status(HttpStatus.CREATED).body(IdeaDTO.from(nuevaIdea));
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<IdeaDTO>> listarPendientes(Authentication authentication) {
        UUID practicanteId = extraerId(authentication);
        List<Idea> ideas = listarIdeasPendientesUseCase.ejecutar(practicanteId);
        List<IdeaDTO> dtos = ideas.stream().map(IdeaDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/procesar")
    public ResponseEntity<Void> procesarIdea(@PathVariable UUID id, Authentication authentication) {
        UUID practicanteId = extraerId(authentication);
        procesarIdeaUseCase.ejecutar(practicanteId, id);
        return ResponseEntity.noContent().build();
    }

    private UUID extraerId(Authentication authentication) {
        PracticanteUserDetails userDetails = (PracticanteUserDetails) authentication.getPrincipal();
        return userDetails.getPracticante().getId();
    }
}
