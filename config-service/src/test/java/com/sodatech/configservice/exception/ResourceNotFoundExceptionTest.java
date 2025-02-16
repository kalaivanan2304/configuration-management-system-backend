package com.sodatech.configservice.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage(), "Exception message should match the provided message");
    }

    @Test
    void testExceptionIsRuntimeException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Test message");

        assertInstanceOf(RuntimeException.class, exception, "ResourceNotFoundException should be a subclass of RuntimeException");
    }
}
