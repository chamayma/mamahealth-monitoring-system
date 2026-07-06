package com.mamahealth.dto.doctor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDoctorRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String specialization;

    @NotBlank
    private String licenseNumber;

    @NotBlank
    private String hospitalName;

    @NotNull
    @Min(0)
    private Integer yearsOfExperience;
}