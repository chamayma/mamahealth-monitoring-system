package com.mamahealth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class LoginRequest {

    @Schema(
            description = "User email address",
            example = "salma@gmail.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @Schema(
            description = "User password",
            example = "Password123")
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
}