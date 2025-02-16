package com.sodatech.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user in the system.
 * Stores user credentials and role information.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique username for authentication.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Encrypted password for authentication.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role assigned to the user (e.g., ROLE_USER, ROLE_ADMIN).
     */
    @Column(nullable = false)
    private String role;
}
