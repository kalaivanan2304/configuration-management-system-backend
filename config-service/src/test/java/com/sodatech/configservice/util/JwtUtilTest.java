package com.sodatech.configservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private String secretKey = "testSecretKeyforconfigurationmanagementsystem";
    private String validToken;
    private String invalidToken = "invalid.jwt.token";

    @BeforeEach
    void setUp() {
        // Inject the secret key into JwtUtil
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);

        // Generate a valid JWT token for testing
        validToken = Jwts.builder()
                .setSubject("testUser")
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 100000))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Test
    void testValidateToken_InvalidToken() {
        assertThrows(JwtException.class, () -> jwtUtil.validateToken(invalidToken));
    }

    @Test
    void testExtractUsername() {
        String username = jwtUtil.extractUsername(validToken);
        assertEquals("testUser", username);
    }

    @Test
    void testExtractRole() {
        String role = jwtUtil.extractRole(validToken);
        assertEquals("ADMIN", role);
    }
}
