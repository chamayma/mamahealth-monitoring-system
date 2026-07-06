package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.recovery.CreateRecoveryRequest;
import com.mamahealth.dto.recovery.RecoveryResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.RecoveryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recovery")
@Validated
@PreAuthorize("hasRole('MOTHER')")
public class RecoveryController {

    private final RecoveryService recoveryService;

    public RecoveryController(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    /**
     * Create Recovery Record
     */
    @PostMapping
    public ResponseEntity<RecoveryResponse> createRecoveryRecord(
            @Valid @RequestBody CreateRecoveryRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        RecoveryResponse response =
                recoveryService.createRecoveryRecord(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get My Recovery History
     */
    @GetMapping("/me/history")
    public ResponseEntity<List<RecoveryResponse>> getMyRecoveryHistory(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<RecoveryResponse> response =
                recoveryService.getMyRecoveryHistory(
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Update Recovery Record
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecoveryResponse> updateRecoveryRecord(
            @PathVariable Long id,
            @Valid @RequestBody CreateRecoveryRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        RecoveryResponse response =
                recoveryService.updateRecoveryRecord(
                        id,
                        request,
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Soft Delete Recovery Record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecoveryRecord(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        recoveryService.deleteRecoveryRecord(
                id,
                userDetails.getUsername());

        return ResponseEntity.ok("Recovery record deleted successfully.");
    }
}