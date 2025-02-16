package com.sodatech.configservice.authorization.filter;

import com.sodatech.configservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * JWT Authentication filter for validating tokens and setting security context.
 */
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Processes incoming requests to validate JWT tokens and set authentication context.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param chain    the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("Processing authentication for request: {}", request.getRequestURI());

        Optional<String> token = extractToken(request);
        if (token.isEmpty()) {
            log.warn("No JWT token found in request headers");
            chain.doFilter(request, response);
            return;
        }

        try {
            authenticateUser(token.get());
            chain.doFilter(request, response);
        } catch (JwtException je) {
            log.error("JWT Authentication failed: {}", je.getMessage());
            handleUnauthorizedResponse(response, je.getMessage());
        } catch (Exception e) {
            log.error("Unexpected authentication error: {}", e.getMessage());
            handleUnauthorizedResponse(response, "Authentication failed");
        }
    }

    /**
     * Extracts the JWT token from the Authorization header.
     *
     * @param request the HTTP request
     * @return an Optional containing the token if present
     */
    private Optional<String> extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return Optional.of(authHeader.substring(7));
        }
        return Optional.empty();
    }

    /**
     * Validates the JWT token and sets the authentication context.
     *
     * @param token the JWT token
     */
    private void authenticateUser(String token) {
        jwtUtil.validateToken(token);
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        log.info("JWT token validated for user: {} with role: {}", username, role);

        UserDetails userDetails = new User(username, "", List.of(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Handles unauthorized responses by sending an error response.
     *
     * @param response the HTTP response
     * @param message  the error message
     * @throws IOException if an error occurs while writing the response
     */
    private void handleUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + message + "\"}");
    }
}
