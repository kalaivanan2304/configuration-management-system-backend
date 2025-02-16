package com.sodatech.userservice.repository;

import com.sodatech.userservice.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUserByUsername() {
        // Given
        User user = User.builder().username("testuser").password("password123").role("ROLE_USER").build();

        // When
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("testuser");

        // Then
        assertTrue(foundUser.isPresent(), "User should be found in the repository");
        assertEquals("testuser", foundUser.get().getUsername(), "Username should match");
    }

    @Test
    void testFindByUsername_NotFound() {
        // When
        Optional<User> foundUser = userRepository.findByUsername("nonexistentuser");

        // Then
        assertFalse(foundUser.isPresent(), "No user should be found for a nonexistent username");
    }
}
