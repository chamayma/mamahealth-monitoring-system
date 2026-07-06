package com.mamahealth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/doctors")
@Validated
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Create Doctor Profile
     */
    @PostMapping
    public ResponseEntity<DoctorResponse> createProfile(
            @Valid @RequestBody CreateDoctorRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.createDoctorProfile(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get Logged-in Doctor Profile
     */
    @GetMapping("/me")
    public ResponseEntity<DoctorResponse> getMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.getMyProfile(
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Update Logged-in Doctor Profile
     */
    @PutMapping("/me")
    public ResponseEntity<DoctorResponse> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody CreateDoctorRequest request) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.updateMyProfile(
                        userDetails.getUsername(),
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Soft Delete Logged-in Doctor Profile
     */
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        doctorService.deleteMyProfile(
                userDetails.getUsername());

        return ResponseEntity.ok(
                "Doctor profile deleted successfully.");
    }
}