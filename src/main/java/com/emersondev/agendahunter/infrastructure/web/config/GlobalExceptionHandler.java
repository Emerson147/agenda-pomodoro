package com.emersondev.agendahunter.infrastructure.web.config;

import com.emersondev.agendahunter.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * El Atajador Global de Errores.
 * Convierte nuestras excepciones puras de Java (DomainException)
 * en respuestas HTTP 400 legibles para Angular.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException ex) {
        // Cuando nuestro dominio grita "¡Límite Anti-Burnout alcanzado!", 
        // evitamos que Spring devuelva un 500 (Error del Servidor) y 
        // devolvemos un 400 Bad Request controlado con el mensaje exacto.
        log.error("Error de dominio (Bad Request): {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", ex.getMessage()));
    }

    // El "otro" para atrapar cualquier error no controlado y avisar en consola y en Postman
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        log.error("Error interno no controlado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado: " + ex.getMessage()));
    }

    // Atajador para contraseñas o correos incorrectos
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException ex) {
        log.warn("Intento de login fallido: Credenciales incorrectas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Correo o contraseña incorrectos"));
    }

    // Atajador para errores de validación de los DTOs (Punto 1 - Formato Estructurado)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        log.warn("Errores de validación de entrada: {}", errores);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Validación fallida en los campos de entrada",
                        "detalles", errores
                ));
    }
}
