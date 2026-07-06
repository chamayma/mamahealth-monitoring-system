package com.mamahealth.dto.common;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ApiResponse(
            boolean success,
            String message,
            T data) {

        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(
            boolean success,
            String message,
            T data,
            Map<String, String> errors) {

        this.success = success;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(
            String message,
            T data) {

        return new ApiResponse<>(
                true,
                message,
                data);
    }

    public static <T> ApiResponse<T> error(
            String message) {

        return new ApiResponse<>(
                false,
                message,
                null);
    }

    public static <T> ApiResponse<T> validationError(
            String message,
            Map<String, String> errors) {

        return new ApiResponse<>(
                false,
                message,
                null,
                errors);
    }
}