package com.sodatech.userservice.service;

import com.sodatech.userservice.entity.User;
import com.sodatech.userservice.exception.ResourceNotFoundException;
import com.sodatech.userservice.repository.UserRepository;
import com.sodatech.userservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().username("testuser").password("password123").role("ROLE_USER").build();
    }

    @Test
    void testRegister_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");

        userService.register(user);

        verify(userRepository, times(1)).save(user);
        assertEquals("hashedPassword", user.getPassword());
        assertEquals("ROLE_USER", user.getRole());
    }

    @Test
    void testRegister_UserAlreadyExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.register(user));
        assertEquals("Username already exists!", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void testLoginUser_Success() {
        User storedUser = new User();
        storedUser.setUsername("testuser");
        storedUser.setPassword("hashedPassword");
        storedUser.setRole("ROLE_USER");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches(user.getPassword(), storedUser.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(storedUser.getUsername(), storedUser.getRole())).thenReturn("mockJwtToken");

        String token = userService.loginUser(user);

        assertNotNull(token);
        assertEquals("mockJwtToken", token);
    }

    @Test
    void testLoginUser_UserNotFound() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userService.loginUser(user));
        assertEquals("User not registered.", exception.getMessage());
    }

    @Test
    void testLoginUser_InvalidPassword() {
        User storedUser = new User();
        storedUser.setUsername("testuser");
        storedUser.setPassword("hashedPassword");
        storedUser.setRole("ROLE_USER");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(storedUser));
        when(passwordEncoder.matches(user.getPassword(), storedUser.getPassword())).thenReturn(false);

        Exception exception = assertThrows(SecurityException.class, () -> userService.loginUser(user));
        assertEquals("Invalid credentials.", exception.getMessage());
    }
}
