package com.sodatech.configservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Utility class for handling JWT token operations such as validation and extraction of claims.
 */
@Component
@Slf4j
@PropertySource("classpath:secret-config.properties")
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey ;

    /**
     * Validates a JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, otherwise false.
     */
    public void validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            log.info("JWT token validated successfully");
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtException("Invalid JWT token");
        }
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username contained in the token.
     */
    public String extractUsername(String token) {
        String username = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        log.info("Extracted username from token: {}", username);
        return username;
    }

    /**
     * Extracts the role from a JWT token.
     *
     * @param token The JWT token.
     * @return The role contained in the token.
     */
    public String extractRole(String token) {
        String role = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");
        log.info("Extracted role from token: {}", role);
        return role;
    }
}
