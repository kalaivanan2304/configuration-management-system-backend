package com.sodatech.userservice.controller;

import com.sodatech.userservice.entity.User;
import com.sodatech.userservice.exception.ResourceNotFoundException;
import com.sodatech.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setRole("ROLE_USER");
    }

    @Test
    void testRegister_Success() {
        doNothing().when(userService).register(user);

        ResponseEntity<String> response = userController.register(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());
        verify(userService, times(1)).register(user);
    }

    @Test
    void testRegister_Failure_UserAlreadyExists() {
        doThrow(new RuntimeException("Username already exists!")).when(userService).register(user);

        Exception exception = assertThrows(RuntimeException.class, () -> userController.register(user));

        assertEquals("Username already exists!", exception.getMessage());
        verify(userService, times(1)).register(user);
    }

    @Test
    void testLogin_Success() {
        String mockToken = "mockJwtToken";
        when(userService.loginUser(user)).thenReturn(mockToken);

        ResponseEntity<String> response = userController.login(user);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockToken, response.getBody());
        verify(userService, times(1)).loginUser(user);
    }

    @Test
    void testLogin_Failure_UserNotFound() {
        when(userService.loginUser(user)).thenThrow(new ResourceNotFoundException("User not registered."));

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> userController.login(user));

        assertEquals("User not registered.", exception.getMessage());
        verify(userService, times(1)).loginUser(user);
    }

    @Test
    void testLogin_Failure_InvalidCredentials() {
        when(userService.loginUser(user)).thenThrow(new SecurityException("Invalid credentials."));

        Exception exception = assertThrows(SecurityException.class, () -> userController.login(user));

        assertEquals("Invalid credentials.", exception.getMessage());
        verify(userService, times(1)).loginUser(user);
    }
}
