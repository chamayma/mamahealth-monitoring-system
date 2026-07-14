package com.mamahealth.dto.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.mamahealth.entity.AppointmentStatus;

import lombok.Data;

@Data
public class AppointmentResponse {

    private Long id;

    private Long motherId;

    private Long doctorId;

    private String motherName;

    // ADD THESE TWO FIELDS
    private String doctorName;

    private String hospitalName;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private String purpose;

    private String notes;

    private AppointmentStatus status;

    private LocalDateTime createdAt;

}