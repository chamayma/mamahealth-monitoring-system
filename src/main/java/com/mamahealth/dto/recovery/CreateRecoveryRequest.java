package com.mamahealth.dto.recovery;

import com.mamahealth.entity.BleedingLevel;
import com.mamahealth.entity.Mobility;
import com.mamahealth.entity.WoundCondition;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRecoveryRequest {

    @NotNull
    private LocalDate recordDate;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer painLevel;

    @NotNull
    @DecimalMin("34.0")
    @DecimalMax("43.0")
    private Double bodyTemperature;

    @NotNull
    private WoundCondition woundCondition;

    @NotNull
    private BleedingLevel bleedingLevel;

    @NotNull
    private Mobility mobility;

    @NotNull
    private Boolean medicationTaken;

    private String notes;

    // Generate getters and setters using your IDE
}