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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/medications")
@Validated
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    /**
     * Doctor prescribes medication
     */
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
     * Mother marks medication completed
     */
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

    @GetMapping("/recent-completed")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<MedicationResponse>>> getRecentMedications() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Recent completed medications retrieved successfully.",

                    medicationService.getRecentMedications()

            )

    );

}

/**
 * Doctor views medications of one mother
 */
@GetMapping("/mother/{motherId}")
@PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
public ResponseEntity<ApiResponse<List<MedicationResponse>>> getMotherMedications(
        @PathVariable Long motherId) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mother medications retrieved successfully.",

                    medicationService.getMotherMedications(motherId)

            )

    );

}

/**
 * Get one medication
 */
@GetMapping("/{id}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<MedicationResponse>> getMedication(
        @PathVariable Long id,
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    MedicationResponse response =
            medicationService.getMedication(
                    id,
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Medication retrieved successfully.",
                    response));

}

/**
 * Update medication
 */
@PutMapping("/{id}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<MedicationResponse>> updateMedication(
        @PathVariable Long id,
        @Valid @RequestBody CreateMedicationRequest request,
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    MedicationResponse response =
            medicationService.updateMedication(
                    id,
                    request,
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Medication updated successfully.",
                    response));

}

/**
 * Doctor views all medications they prescribed
 */
@GetMapping("/doctor/me")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<MedicationResponse>>> getDoctorMedications(
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    List<MedicationResponse> response =
            medicationService.getDoctorMedications(
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Doctor medications retrieved successfully.",
                    response));
}

}
