package com.mamahealth.dto.doctor;

import lombok.Data;

@Data
public class DoctorResponse {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String specialization;

    private String licenseNumber;

    private String hospitalName;

    private Integer yearsOfExperience;

    private String email;
    private Boolean active;
}