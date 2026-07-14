package com.mamahealth.dto.recovery;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mamahealth.entity.BleedingLevel;
import com.mamahealth.entity.Mobility;
import com.mamahealth.entity.WoundCondition;

import lombok.Data;

@Data
public class RecoveryResponse {

    private Long id;
    private String motherName;
    private LocalDate recordDate;
    private Integer painLevel;
    private Double bodyTemperature;
    private WoundCondition woundCondition;
    private BleedingLevel bleedingLevel;
    private Mobility mobility;
    private Boolean medicationTaken;
    private String notes;
    private LocalDateTime createdAt;
    private String recoveryIndicator;


}