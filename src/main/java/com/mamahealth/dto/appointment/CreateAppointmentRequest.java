package com.mamahealth.dto.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAppointmentRequest {

    @NotNull(message = "Mother ID is required")
    private Long motherId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    @NotBlank(message = "Purpose is required")
    @Size(max = 255)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Purpose must be a valid text value")
    private String purpose;

    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]*$", message = "Notes must be valid text")
    private String notes;

    
}