package com.mamahealth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/doctors")
@Validated
@PreAuthorize("hasRole('DOCTOR')")
@Tag(
        name = "Doctor Management",
        description = "APIs for managing doctor profiles")
@SecurityRequirement(name = "Bearer Authentication")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    /**
     * Create Doctor Profile
     */
    @Operation(
            summary = "Create Doctor Profile",
            description = "Creates a profile for the authenticated doctor.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Doctor profile created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Doctor profile already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> createProfile(
            @Valid @RequestBody CreateDoctorRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.createDoctorProfile(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor profile created successfully.",
                        response));
    }

    /**
     * Get Logged-in Doctor Profile
     */
    @Operation(
            summary = "Get My Profile",
            description = "Returns the authenticated doctor's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor profile not found")
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<DoctorResponse>> getMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.getMyProfile(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor profile retrieved successfully.",
                        response));
    }

    /**
     * Update Logged-in Doctor Profile
     */
    @Operation(
            summary = "Update My Profile",
            description = "Updates the authenticated doctor's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor profile not found")
    })
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody CreateDoctorRequest request) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorResponse response =
                doctorService.updateMyProfile(
                        userDetails.getUsername(),
                        request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor profile updated successfully.",
                        response));
    }

    /**
     * Delete Logged-in Doctor Profile
     */
    @Operation(
            summary = "Delete My Profile",
            description = "Soft deletes the authenticated doctor's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor profile not found")
    })
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        doctorService.deleteMyProfile(
                userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor profile deleted successfully.",
                        null));
    }
}
