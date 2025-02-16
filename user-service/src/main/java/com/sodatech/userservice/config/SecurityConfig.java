package com.sodatech.userservice.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration class that defines security settings for the application.
 * This class configures HTTP security, disables CSRF, and sets session management to stateless.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Slf4j
public class SecurityConfig {

    /**
     * Configures security settings such as disabling CSRF, allowing all requests,
     * and enforcing stateless session management.
     *
     * @param http HttpSecurity object to configure security settings.
     * @return Configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Initializing security configurations");

        http
                .csrf(csrf -> {
                    log.info("Disabling CSRF protection");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                        new AntPathRequestMatcher("/sodatech/v1/user/**"))
                        .permitAll())
                .sessionManagement(session -> {
                    log.info("Setting session management policy to STATELESS");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        log.info("Security configurations initialized successfully");
        return http.build();
    }
}
