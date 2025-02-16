package com.sodatech.userservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private static final String TEST_SECRET_KEY = "testSecretKeytestSecretKeytestSecretKeytestSecretKey";
    private static final String TEST_USERNAME = "testUser";
    private static final String TEST_ROLE = "USER";

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("jwt.secret-key", () -> TEST_SECRET_KEY);
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Inject the test secret key using Reflection (since @Value is not directly settable)
        var field = JwtUtil.class.getDeclaredField("secretKey");
        field.setAccessible(true);
        field.set(jwtUtil, TEST_SECRET_KEY);
    }

    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_ROLE);
        assertNotNull(token, "Generated token should not be null");
    }

    @Test
    void testTokenContents() {
        String token = jwtUtil.generateToken(TEST_USERNAME, TEST_ROLE);
        Claims claims = Jwts.parser()
                .setSigningKey(TEST_SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        assertEquals(TEST_USERNAME, claims.getSubject(), "Token subject should match username");
        assertEquals(TEST_ROLE, claims.get("role"), "Token should contain correct role");
        assertNotNull(claims.getExpiration(), "Token should have an expiration date");
    }
}
