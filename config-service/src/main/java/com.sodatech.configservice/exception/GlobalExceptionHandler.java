package com.sodatech.configservice.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage application exceptions and return meaningful HTTP responses.
 */
@RestControllerAdvice
@Slf4j
class GlobalExceptionHandler {

    /**
     * Handles all client-side (400) errors.
     *
     * @param ex The exception thrown.
     * @return ResponseEntity with error message and HTTP status.
     */
    @ExceptionHandler({IllegalArgumentException.class, JwtException.class})
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        log.error("Client error occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles Unauthorized (401) errors.
     *
     * @return ResponseEntity with error message and HTTP status.
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> handleUnauthorized(SecurityException ex) {
        log.error("Unauthorized access: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
    }

    /**
     * Handles Forbidden (403) errors.
     *
     * @return ResponseEntity with error message and HTTP status.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleForbidden(AccessDeniedException ex) {
        log.error("Forbidden access: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: " + ex.getMessage());
    }

    /**
     * Handles Not Found (404) errors.
     *
     * @return ResponseEntity with error message and HTTP status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles all server-side (500) errors.
     *
     * @param ex The exception thrown.
     * @return ResponseEntity with error message and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleServerErrors(Exception ex) {
        log.error("Server error occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}