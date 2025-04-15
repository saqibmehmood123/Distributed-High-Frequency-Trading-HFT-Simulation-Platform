package com.hft.exceptionhandling;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;          // HTTP status code (e.g., 400, 404)
    private String message;      // Error description
    private LocalDateTime timestamp;  // When the error occurred

    // Constructor
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters (required for JSON serialization)
    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}