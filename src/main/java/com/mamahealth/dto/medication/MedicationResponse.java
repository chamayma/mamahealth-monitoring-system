package com.mamahealth.dto.medication;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.mamahealth.entity.MedicationStatus;

import lombok.Data;

@Data
public class MedicationResponse {

    private Long id;

    private Long motherId;

    private Long doctorId;

    // New field for Doctor Dashboard
    private String motherName;

    private String doctorName;

    private String medicationName;

    private String dosage;

    private String frequency;

    private LocalDate startDate;

    private LocalDate endDate;

    private String instructions;

    private MedicationStatus status;

    // New field
    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

}