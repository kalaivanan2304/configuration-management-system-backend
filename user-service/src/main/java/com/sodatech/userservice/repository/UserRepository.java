package com.sodatech.userservice.repository;

import com.sodatech.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entity interactions with the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user.
     * @return An Optional containing the User if found, otherwise empty.
     */
    Optional<User> findByUsername(String username);
}
