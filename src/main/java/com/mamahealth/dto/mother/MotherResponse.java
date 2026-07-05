package com.mamahealth.dto.mother;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MotherResponse {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String address;

    private String bloodGroup;

    private String emergencyContact;

    private LocalDate deliveryDate;

    private String hospitalName;

    private String email;
}