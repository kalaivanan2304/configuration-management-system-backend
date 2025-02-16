package com.sodatech.userservice.exception;

/**
 * Custom exception for handling resource conflict scenarios.
 */
public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
