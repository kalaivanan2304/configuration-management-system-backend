package com.sodatech.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        // Ensures the Spring Boot application starts up correctly
        assertTrue(true, "Application context loaded successfully");
    }
}
