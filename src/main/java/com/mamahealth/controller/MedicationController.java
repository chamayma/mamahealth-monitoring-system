package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.medication.CreateMedicationRequest;
import com.mamahealth.dto.medication.MedicationResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.MedicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/medications")
@Validated
@Tag(
        name = "Medication Management",
        description = "APIs for prescribing and managing medications")
@SecurityRequirement(name = "Bearer Authentication")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Doctor prescribes medication
     */
    @Operation(
            summary = "Prescribe Medication",
            description = "Allows a doctor to prescribe medication to a mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Medication prescribed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother or Doctor not found")
    })
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<MedicationResponse>> prescribeMedication(
            @Valid @RequestBody CreateMedicationRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MedicationResponse response =
                medicationService.prescribeMedication(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Medication prescribed successfully.",
                        response));
    }

    /**
     * Mother views medications
     */
    @Operation(
            summary = "Get My Medications",
            description = "Returns all medications for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medications retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<List<MedicationResponse>>> getMyMedications(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<MedicationResponse> response =
                medicationService.getMyMedications(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medications retrieved successfully.",
                        response));
    }

    /**
     * Doctor updates medication
     */
    @Operation(
            summary = "Update Medication",
            description = "Updates an existing medication.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medication updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<MedicationResponse>> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody CreateMedicationRequest request) {

        MedicationResponse response =
                medicationService.updateMedication(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medication updated successfully.",
                        response));
    }

    /**
     * Mother marks medication completed
     */
    @Operation(
            summary = "Complete Medication",
            description = "Marks a medication as completed.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medication marked as completed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<MedicationResponse>> markCompleted(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MedicationResponse response =
                medicationService.markCompleted(
                        id,
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medication marked as completed.",
                        response));
    }

    /**
     * Doctor deletes medication
     */
    @Operation(
            summary = "Delete Medication",
            description = "Soft deletes a medication.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Medication deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Medication not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Void>> deleteMedication(
            @PathVariable Long id) {

        medicationService.deleteMedication(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Medication deleted successfully.",
                        null));
    }
}
