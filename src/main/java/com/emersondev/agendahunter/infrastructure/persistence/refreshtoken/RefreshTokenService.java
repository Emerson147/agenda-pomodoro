package com.emersondev.agendahunter.infrastructure.persistence.refreshtoken;

import com.emersondev.agendahunter.domain.exception.DomainException;
import com.emersondev.agendahunter.infrastructure.persistence.practicante.PracticanteJpaEntity;
import com.emersondev.agendahunter.infrastructure.persistence.practicante.SpringDataPracticanteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private final SpringDataRefreshTokenRepository refreshTokenRepository;
    private final SpringDataPracticanteRepository practicanteRepository;

    @Transactional
    public RefreshTokenJpaEntity crearRefreshToken(UUID practicanteId) {
        PracticanteJpaEntity practicante = practicanteRepository.findById(practicanteId)
                .orElseThrow(() -> new DomainException("Practicante no encontrado para crear refresh token"));

        // Invalidar todos los refresh tokens anteriores del usuario (rotacion de tokens)
        refreshTokenRepository.deleteByPracticanteId(practicanteId);

        RefreshTokenJpaEntity refreshToken = RefreshTokenJpaEntity.builder()
                .practicante(practicante)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .build();

        log.info("Refresh token creado para practicante ID: {}", practicanteId);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenJpaEntity validarRefreshToken(String token) {
        RefreshTokenJpaEntity refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new DomainException("Refresh token inválido o no encontrado."));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            log.warn("Intento de uso de refresh token expirado para practicante ID: {}", refreshToken.getPracticante().getId());
            throw new DomainException("Refresh token expirado. Inicia sesión nuevamente.");
        }

        return refreshToken;
    }

    @Transactional
    public void revocarRefreshToken(UUID practicanteId) {
        refreshTokenRepository.deleteByPracticanteId(practicanteId);
        log.info("Refresh tokens revocados para practicante ID: {}", practicanteId);
    }
}
