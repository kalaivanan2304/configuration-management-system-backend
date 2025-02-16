package com.sodatech.userservice.exception;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleBadRequest_WithIllegalArgumentException() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid request data");
        ResponseEntity<String> response = exceptionHandler.handleBadRequest(ex);

        assertEquals(BAD_REQUEST, response.getStatusCode(), "Expected 400 BAD_REQUEST status");
        assertEquals("Invalid request data", response.getBody(), "Expected error message in response");
    }

    @Test
    void testHandleBadRequest_WithJwtException() {
        JwtException ex = new JwtException("Invalid JWT token");
        ResponseEntity<String> response = exceptionHandler.handleBadRequest(ex);

        assertEquals(BAD_REQUEST, response.getStatusCode(), "Expected 400 BAD_REQUEST status");
        assertEquals("Invalid JWT token", response.getBody(), "Expected error message in response");
    }

    @Test
    void testHandleUnauthorized() {
        SecurityException ex = new SecurityException("Invalid credentials");
        ResponseEntity<String> response = exceptionHandler.handleUnauthorized(ex);

        assertEquals(UNAUTHORIZED, response.getStatusCode(), "Expected 401 UNAUTHORIZED status");
        assertEquals("Unauthorized: Invalid credentials", response.getBody(), "Expected unauthorized message");
    }

    @Test
    void testHandleForbidden() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");
        ResponseEntity<String> response = exceptionHandler.handleForbidden(ex);

        assertEquals(FORBIDDEN, response.getStatusCode(), "Expected 403 FORBIDDEN status");
        assertEquals("Forbidden: Access denied", response.getBody(), "Expected forbidden message");
    }

    @Test
    void testHandleNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("User not found");
        ResponseEntity<String> response = exceptionHandler.handleNotFound(ex);

        assertEquals(NOT_FOUND, response.getStatusCode(), "Expected 404 NOT_FOUND status");
        assertEquals("User not found", response.getBody(), "Expected not found message");
    }

    @Test
    void testHandleServerErrors() {
        Exception ex = new Exception("Unexpected server failure");
        ResponseEntity<String> response = exceptionHandler.handleServerErrors(ex);

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode(), "Expected 500 INTERNAL_SERVER_ERROR status");
        assertEquals("An unexpected error occurred", response.getBody(), "Expected generic error message");
    }
}
