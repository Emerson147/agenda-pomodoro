package com.emersondev.agendahunter.infrastructure.web.auth;

import com.emersondev.agendahunter.domain.model.Practicante;
import com.emersondev.agendahunter.domain.repository.PracticanteRepository;
import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.infrastructure.config.security.JwtService;
import com.emersondev.agendahunter.infrastructure.config.security.PracticanteUserDetails;
import com.emersondev.agendahunter.infrastructure.persistence.practicante.PracticanteJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.practicante.SpringDataPracticanteRepository;
import com.emersondev.agendahunter.infrastructure.persistence.refreshtoken.RefreshTokenJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PracticanteRepository practicanteRepository;
    private final SpringDataPracticanteRepository springDataPracticanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Iniciando registro para el email: {}", request.getEmail());

        if (practicanteRepository.buscarPorEmail(request.getEmail()).isPresent()) {
            log.warn("Intento de registro fallido: el email {} ya está en uso", request.getEmail());
            throw new DomainException("El correo electrónico ya se encuentra registrado.");
        }

        var practicante = new Practicante(
                UUID.randomUUID(),
                request.getNombre(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        practicanteRepository.guardar(practicante);
        log.info("Practicante registrado exitosamente con ID: {}", practicante.getId());

        var userDetails = new PracticanteUserDetails(practicante);
        var accessToken = jwtService.generateToken(userDetails);
        var refreshTokenEntity = refreshTokenService.crearRefreshToken(practicante.getId());

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshTokenEntity.getToken())
                .practicante(PracticanteDTO.from(practicante))
                .build();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Iniciando proceso de login para el email: {}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var practicante = practicanteRepository.buscarPorEmail(request.getEmail())
                .orElseThrow(() -> new DomainException("Practicante no encontrado"));
        log.info("Login exitoso para el practicante ID: {}", practicante.getId());

        var userDetails = new PracticanteUserDetails(practicante);
        var accessToken = jwtService.generateToken(userDetails);
        var refreshTokenEntity = refreshTokenService.crearRefreshToken(practicante.getId());

        return AuthResponse.builder()
                .token(accessToken)
                .refreshToken(refreshTokenEntity.getToken())
                .practicante(PracticanteDTO.from(practicante))
                .build();
    }

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        log.info("Renovando token de acceso...");

        RefreshTokenJpaEntity refreshTokenEntity = refreshTokenService.validarRefreshToken(request.getRefreshToken());
        PracticanteJpaEntity practicanteEntity = refreshTokenEntity.getPracticante();

        var practicante = practicanteRepository.buscarPorEmail(practicanteEntity.getEmail())
                .orElseThrow(() -> new DomainException("Practicante no encontrado"));

        var userDetails = new PracticanteUserDetails(practicante);
        var newAccessToken = jwtService.generateToken(userDetails);
        // Rotacion: crear nuevo refresh token e invalidar el anterior
        var newRefreshTokenEntity = refreshTokenService.crearRefreshToken(practicante.getId());

        log.info("Token renovado exitosamente para: {}", practicanteEntity.getEmail());

        return AuthResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshTokenEntity.getToken())
                .practicante(PracticanteDTO.from(practicante))
                .build();
    }

    @Transactional
    public void logout(UUID practicanteId) {
        refreshTokenService.revocarRefreshToken(practicanteId);
        log.info("Logout ejecutado para practicante ID: {}", practicanteId);
    }
}
