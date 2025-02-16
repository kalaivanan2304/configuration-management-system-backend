package com.sodatech.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the User Service application.
 * This service handles user authentication, registration, and role management.
 */
@SpringBootApplication
@Slf4j
public class UserServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        log.info("Starting User Service Application...");
        SpringApplication.run(UserServiceApplication.class, args);
        log.info("User Service Application started successfully.");
    }
}
