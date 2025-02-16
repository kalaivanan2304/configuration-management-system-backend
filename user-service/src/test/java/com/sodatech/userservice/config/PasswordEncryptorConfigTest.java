package com.sodatech.userservice.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorConfigTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new PasswordEncryptorConfig().passwordEncoder();
    }

    @Test
    void testPasswordEncoderBeanCreation() {
        assertNotNull(passwordEncoder, "PasswordEncoder bean should not be null");
        assertInstanceOf(BCryptPasswordEncoder.class, passwordEncoder, "Bean should be an instance of BCryptPasswordEncoder");
    }

    @Test
    void testPasswordEncoding() {
        String rawPassword = "mySecurePassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertNotNull(encodedPassword, "Encoded password should not be null");
        assertNotEquals(rawPassword, encodedPassword, "Encoded password should not match raw password");
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Encoded password should match raw password");
    }

    @Test
    void testDifferentEncodingsForSamePassword() {
        String rawPassword = "testPassword";
        String encodedPassword1 = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);

        assertNotEquals(encodedPassword1, encodedPassword2, "BCrypt should generate different hashes for the same password due to salting");
    }

    @Test
    void testPasswordEncodingWithNull_ShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> passwordEncoder.encode(null), "Encoding a null password should throw an exception");
    }

    @Test
    void testInvalidPasswordMatching() {
        String rawPassword = "correctPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertFalse(passwordEncoder.matches("wrongPassword", encodedPassword), "Wrong password should not match encoded password");
    }
}
