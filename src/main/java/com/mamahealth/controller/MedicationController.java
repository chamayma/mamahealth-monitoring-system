package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MedicationResponse> prescribeMedication(
            @Valid @RequestBody CreateMedicationRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MedicationResponse response =
                medicationService.prescribeMedication(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mother views her medications
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<List<MedicationResponse>> getMyMedications(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<MedicationResponse> response =
                medicationService.getMyMedications(
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor updates medication
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<MedicationResponse> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody CreateMedicationRequest request) {

        MedicationResponse response =
                medicationService.updateMedication(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Mother marks medication as completed
     */
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<MedicationResponse> markCompleted(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MedicationResponse response =
                medicationService.markCompleted(
                        id,
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor deletes medication
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> deleteMedication(
            @PathVariable Long id) {

        medicationService.deleteMedication(id);

        return ResponseEntity.ok("Medication deleted successfully.");
    }
}