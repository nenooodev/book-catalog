package com.example.catalog.dto;

import java.time.Instant;

public record ErrorResponseDTO(String error, String message, Instant timestamp) {
    public ErrorResponseDTO(String error, String message) {
        this(error, message, Instant.now());
    }
}