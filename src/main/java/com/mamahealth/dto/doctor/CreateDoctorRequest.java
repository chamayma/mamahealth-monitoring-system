package com.mamahealth.dto.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateDoctorRequest {

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Full name must contain letters and spaces only")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Specialization is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Specialization must contain letters and spaces only")
    private String specialization;

    @NotBlank(message = "License number is required")
    @Pattern(regexp = "^[A-Za-z0-9-]+$", message = "License number may contain only letters, numbers, and hyphens")
    private String licenseNumber;

    @NotBlank(message = "Hospital name is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Hospital name must contain letters and spaces only")
    private String hospitalName;

    @NotNull
    @Min(0)
    private Integer yearsOfExperience;
}