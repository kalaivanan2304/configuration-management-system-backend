package com.sodatech.configservice.exception;

/**
 * Custom exception for handling resource not found scenarios.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
