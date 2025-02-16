package com.sodatech.userservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testResourceNotFoundExceptionMessage() {
        String errorMessage = "User not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertNotNull(exception, "Exception object should not be null");
        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the provided message");
    }

    @Test
    void testResourceNotFoundExceptionWithNullMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException(null);

        assertNull(exception.getMessage(), "Exception message should be null when passed as null");
    }
}
