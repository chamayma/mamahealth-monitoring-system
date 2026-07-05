package com.mamahealth.dto.mother;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateMotherRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String address;

    @NotBlank
    private String bloodGroup;

    @NotBlank
    private String emergencyContact;

    @NotNull
    private LocalDate deliveryDate;

    @NotBlank
    private String hospitalName;
}