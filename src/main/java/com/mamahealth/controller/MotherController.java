package com.mamahealth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.mother.CreateMotherRequest;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.MotherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mothers")
@Validated
@PreAuthorize("hasRole('MOTHER')")
@Tag(
        name = "Mother Management",
        description = "APIs for managing mother profiles")
@SecurityRequirement(name = "Bearer Authentication")
public class MotherController {

    private final MotherService motherService;

    public MotherController(MotherService motherService) {
        this.motherService = motherService;
    }

    @Operation(
            summary = "Create Mother Profile",
            description = "Creates a profile for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Mother profile created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Mother profile already exists")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<MotherResponse>> createProfile(
            @Valid @RequestBody CreateMotherRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MotherResponse response =
                motherService.createMotherProfile(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Mother profile created successfully.",
                        response));
    }

    @Operation(
            summary = "Get My Profile",
            description = "Returns the authenticated mother's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother profile not found")
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MotherResponse>> getMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MotherResponse response =
                motherService.getMyProfile(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mother profile retrieved successfully.",
                        response));
    }

    @Operation(
            summary = "Update My Profile",
            description = "Updates the authenticated mother's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother profile not found")
    })
    @PutMapping("/me")
    public ResponseEntity<ApiResponse<MotherResponse>> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody CreateMotherRequest request) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MotherResponse response =
                motherService.updateMyProfile(
                        userDetails.getUsername(),
                        request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mother profile updated successfully.",
                        response));
    }

    @Operation(
            summary = "Delete My Profile",
            description = "Soft deletes the authenticated mother's profile.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Profile deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother profile not found")
    })
    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMyProfile(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        motherService.deleteMyProfile(
                userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mother profile deleted successfully.",
                        null));
    }
}