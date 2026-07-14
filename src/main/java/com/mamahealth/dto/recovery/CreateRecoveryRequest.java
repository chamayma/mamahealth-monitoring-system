package com.mamahealth.dto.recovery;

import com.mamahealth.entity.BleedingLevel;
import com.mamahealth.entity.Mobility;
import com.mamahealth.entity.WoundCondition;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateRecoveryRequest {

    @NotNull(message = "Record date is required")
    private LocalDate recordDate;

    @NotNull(message = "Pain level is required")
    @Min(value = 1, message = "Pain level must be between 1 and 10")
    @Max(value = 10, message = "Pain level must be between 1 and 10")
    private Integer painLevel;

    @NotNull(message = "Body temperature is required")
    @DecimalMin(value = "34.0", message = "Body temperature must be between 34.0 and 43.0")
    @DecimalMax(value = "43.0", message = "Body temperature must be between 34.0 and 43.0")
    private Double bodyTemperature;

    @NotNull(message = "Wound condition is required")
    private WoundCondition woundCondition;

    @NotNull(message = "Bleeding level is required")
    private BleedingLevel bleedingLevel;

    @NotNull(message = "Mobility is required")
    private Mobility mobility;

    @NotNull(message = "Medication taken flag is required")
    private Boolean medicationTaken;

    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]*$", message = "Notes must be valid text")
    private String notes;

}