package com.mamahealth.dto.mother;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateMotherRequest {

    @NotBlank(message = "Full name is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Full name must contain letters and spaces only")
    private String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Address must contain letters, numbers, and common punctuation only")
    private String address;

    @NotBlank(message = "Blood group is required")
    @Pattern(regexp = "^[ABO]{1,2}[+-]$", message = "Blood group must be a valid type like A+, O-, AB+")
    private String bloodGroup;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{10}$", message = "Emergency contact must be exactly 10 digits")
    private String emergencyContact;

    @NotNull(message = "Delivery date is required")
    private LocalDate deliveryDate;

    @NotBlank(message = "Hospital name is required")
    @Pattern(regexp = "^[\\p{L} .'-]+$", message = "Hospital name must contain letters and spaces only")
    private String hospitalName;
}