package com.sodatech.userservice.service;

import com.sodatech.userservice.exception.ResourceConflictException;
import com.sodatech.userservice.exception.ResourceNotFoundException;
import com.sodatech.userservice.util.JwtUtil;
import com.sodatech.userservice.entity.User;
import com.sodatech.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related operations such as registration and authentication.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    /**
     * Registers a new user in the system.
     *
     * @param user The user entity containing username and password.
     * @throws RuntimeException If the username is already taken.
     */
    public void register(User user) {
        log.info("Attempting to register user: {}", user.getUsername());

        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            log.warn("Username '{}' already exists!", user.getUsername());
            throw new ResourceConflictException("Username already exists!");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set a default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_USER");
        }

        // Save the user in the database
        userRepository.save(user);
        log.info("User '{}' registered successfully", user.getUsername());
    }

    /**
     * Authenticates the user and returns a JWT token on successful login.
     *
     * @param user The user entity containing login credentials.
     * @return The JWT token if authentication is successful.
     * @throws ResourceNotFoundException If the user is not found.
     * @throws RuntimeException If the provided credentials are incorrect.
     */
    public String loginUser(User user) {
        log.info("User '{}' attempting to log in", user.getUsername());

        Optional<User> userOpt = userRepository.findByUsername(user.getUsername());
        if (!userOpt.isPresent()) {
            log.error("Login failed: User '{}' not found", user.getUsername());
            throw new ResourceNotFoundException("User not registered.");
        }

        User registeredUser = userOpt.get();
        if (passwordEncoder.matches(user.getPassword(), registeredUser.getPassword())) {
            String token = jwtUtil.generateToken(registeredUser.getUsername(), registeredUser.getRole());
            log.info("Login successful for user '{}', JWT token generated", user.getUsername());
            return token;
        }

        log.error("Login failed: Invalid credentials for user '{}'", user.getUsername());
        throw new SecurityException("Invalid credentials.");
    }
}
