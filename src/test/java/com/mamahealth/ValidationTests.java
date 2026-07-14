package com.mamahealth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mamahealth.dto.auth.SignupRequest;
import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.mother.CreateMotherRequest;

class ValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldRejectMotherPhoneAndEmergencyContactWhenNot10Digits() {
        CreateMotherRequest request = new CreateMotherRequest();
        request.setFullName("Test User");
        request.setPhoneNumber("12345");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setAddress("123 Main St");
        request.setBloodGroup("A+");
        request.setEmergencyContact("999");
        request.setDeliveryDate(LocalDate.of(2025, 1, 1));
        request.setHospitalName("City Hospital");

        Set<ConstraintViolation<CreateMotherRequest>> violations = validator.validate(request);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("emergencyContact")));
    }

    @Test
    void shouldRejectDoctorPhoneWhenNot10Digits() {
        CreateDoctorRequest request = new CreateDoctorRequest();
        request.setFullName("Doctor Name");
        request.setPhoneNumber("012345678");
        request.setSpecialization("Obstetrics");
        request.setLicenseNumber("DOC-1234");
        request.setHospitalName("City Hospital");
        request.setYearsOfExperience(5);

        Set<ConstraintViolation<CreateDoctorRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phoneNumber")));
    }

    @Test
    void shouldRejectSignupPasswordWhenTooShort() {
        SignupRequest request = new SignupRequest();
        request.setEmail("user@example.com");
        request.setPassword("short");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }
}
