package com.sodatech.userservice.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for JWT token operations, including token generation and validation.
 */
@Component
@Slf4j
@PropertySource("classpath:secret-config.properties")
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey ;



    private static final long EXPIRATION_TIME = 900000; // 15 minutes

    /**
     * Generates a JWT token for a given user with their role.
     *
     * @param userName The username for whom the token is generated.
     * @param role The role of the user.
     * @return The generated JWT token.
     */
    public String generateToken(String userName, String role) {
        log.info("Generating JWT token for user: {}", userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        String token = createToken(claims, userName);
        log.info("JWT token generated successfully for user: {}", userName);
        return token;
    }

    /**
     * Creates a JWT token with specified claims and subject.
     *
     * @param claims  The claims to be included in the token.
     * @param subject The subject (username) for the token.
     * @return The JWT token as a String.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        log.debug("Creating JWT token for subject: {}", subject);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
