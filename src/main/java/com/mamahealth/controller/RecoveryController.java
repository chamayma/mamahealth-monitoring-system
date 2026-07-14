package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.recovery.CreateRecoveryRequest;
import com.mamahealth.dto.recovery.RecoveryResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.RecoveryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recovery")
@Validated
public class RecoveryController {

    private final RecoveryService recoveryService;

    public RecoveryController(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    /**
     * Create Recovery Record
     */
    @PostMapping
    @PreAuthorize("hasRole('MOTHER')")

    public ResponseEntity<ApiResponse<RecoveryResponse>> createRecoveryRecord(
            @Valid @RequestBody CreateRecoveryRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        RecoveryResponse response =
                recoveryService.createRecoveryRecord(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Recovery record created successfully.",
                        response));
    }

    /**
     * Get Recovery History
     */
    @GetMapping("/me/history")
    @PreAuthorize("hasRole('MOTHER')")

    public ResponseEntity<ApiResponse<List<RecoveryResponse>>> getMyRecoveryHistory(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<RecoveryResponse> response =
                recoveryService.getMyRecoveryHistory(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recovery history retrieved successfully.",
                        response));
    }

    /**
     * Update Recovery Record
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MOTHER')")

    public ResponseEntity<ApiResponse<RecoveryResponse>> updateRecoveryRecord(
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

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recovery record updated successfully.",
                        response));
    }

    /**
     * Delete Recovery Record
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MOTHER')")

    public ResponseEntity<ApiResponse<Void>> deleteRecoveryRecord(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        recoveryService.deleteRecoveryRecord(
                id,
                userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recovery record deleted successfully.",
                        null));
    }

    @GetMapping("/mother/{motherId}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<RecoveryResponse>>> getMotherRecoveryHistory(
        @PathVariable Long motherId) {

    List<RecoveryResponse> response =
            recoveryService.getMotherRecoveryHistory(motherId);

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Recovery history retrieved successfully.",

                    response

            )

    );

}

@GetMapping("/recent")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<RecoveryResponse>>> getRecentRecoveries() {

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Recent recoveries retrieved successfully.",
                    recoveryService.getRecentRecoveries()));
}
}