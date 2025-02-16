package com.sodatech.userservice.controller;

import com.sodatech.userservice.entity.User;
import com.sodatech.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing user authentication and registration.
 * Provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/sodatech/v1/user")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Handles user registration.
     *
     * @param user The user details to be registered.
     * @return ResponseEntity with success message.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        log.info("Received registration request for user: {}", user.getUsername());
        userService.register(user);
        log.info("User registered successfully: {}", user.getUsername());
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Handles user login and returns a JWT token if authentication is successful.
     *
     * @param user The user credentials for login.
     * @return ResponseEntity with JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        log.info("Received login request for user: {}", user.getUsername());
        String token = userService.loginUser(user);
        log.info("User logged in successfully: {}", user.getUsername());
        return ResponseEntity.ok(token);
    }
}
