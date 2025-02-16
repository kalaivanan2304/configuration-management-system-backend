package com.sodatech.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class for password encryption.
 * This class provides a bean for encoding passwords using BCrypt hashing algorithm.
 */
@Configuration
@Slf4j
public class PasswordEncryptorConfig {

    /**
     * Creates a PasswordEncoder bean that uses BCrypt hashing algorithm.
     *
     * @return An instance of {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Initializing BCryptPasswordEncoder bean");
        return new BCryptPasswordEncoder();
    }
}