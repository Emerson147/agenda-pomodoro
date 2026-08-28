package com.emersondev.agendahunter.infrastructure.web.auth;

import com.emersondev.agendahunter.infrastructure.config.security.PracticanteUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        log.info("REST request - Registro de nuevo usuario");
        return ResponseEntity.ok(authService.register(request));
    }
`
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        log.info("REST request - Autenticación de usuario");
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        log.info("REST request - Renovación de tokens");
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication) {
        PracticanteUserDetails userDetails = (PracticanteUserDetails) authentication.getPrincipal();
        authService.logout(userDetails.getPracticante().getId());
        log.info("REST request - Logout ejecutado");
        return ResponseEntity.noContent().build();
    }
}
