package com.mamahealth.dto.medication;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateMedicationRequest {

    @NotNull(message = "Mother ID is required")
    private Long motherId;

    @NotBlank(message = "Medication name is required")
    @Size(max = 100)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Medication name must be valid text")
    private String medicationName;

    @NotBlank(message = "Dosage is required")
    @Size(max = 100)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Dosage must be valid text")
    private String dosage;

    @NotBlank(message = "Frequency is required")
    @Size(max = 100)
    @Pattern(regexp = "^[\\p{L}0-9 /,.'()\\-]+$", message = "Frequency must be valid text")
    private String frequency;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]*$", message = "Instructions must be valid text")
    private String instructions;

}