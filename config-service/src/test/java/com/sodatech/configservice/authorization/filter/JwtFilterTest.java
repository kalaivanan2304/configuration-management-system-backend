package com.sodatech.configservice.authorization.filter;

import com.sodatech.configservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter printWriter;

    @Mock
    private FilterChain chain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testValidToken_AuthenticationSuccessful() throws ServletException, IOException {
        // Given
        String token = "valid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        doNothing().when(jwtUtil).validateToken(token);
        when(jwtUtil.extractUsername(token)).thenReturn("testUser");
        when(jwtUtil.extractRole(token)).thenReturn("ROLE_USER");

        // When
        jwtFilter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should be set");
    }

    @Test
    void testNoToken_PassesRequestWithoutAuthentication() throws ServletException, IOException {
        // Given
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        jwtFilter.doFilterInternal(request, response, chain);

        // Then
        verify(chain, times(1)).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(), "Authentication should not be set");
    }

    @Test
    void testInvalidToken_AuthenticationFails() throws ServletException, IOException {
        // Given
        String token = "invalid.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(response.getWriter()).thenReturn(printWriter);
        doThrow(new JwtException("Invalid token")).when(jwtUtil).validateToken(token);

        // When
        jwtFilter.doFilterInternal(request, response, chain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, times(1)).getWriter();
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void testExpiredToken_AuthenticationFails() throws ServletException, IOException {
        // Given
        String token = "expired.jwt.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(response.getWriter()).thenReturn(printWriter);
        doThrow(new JwtException("Token expired")).when(jwtUtil).validateToken(token);

        // When
        jwtFilter.doFilterInternal(request, response, chain);

        // Then
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response, times(1)).getWriter();
        verify(chain, never()).doFilter(request, response);
    }
}
