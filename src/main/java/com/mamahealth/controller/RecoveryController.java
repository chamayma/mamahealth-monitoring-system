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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/recovery")
@Validated
@PreAuthorize("hasRole('MOTHER')")
@Tag(
        name = "Recovery Management",
        description = "APIs for managing recovery records")
@SecurityRequirement(name = "Bearer Authentication")
public class RecoveryController {

    private final RecoveryService recoveryService;

    public RecoveryController(RecoveryService recoveryService) {
        this.recoveryService = recoveryService;
    }

    /**
     * Create Recovery Record
     */
    @Operation(
            summary = "Create Recovery Record",
            description = "Creates a recovery record for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Recovery record created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
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
    @Operation(
            summary = "Get Recovery History",
            description = "Returns all recovery records for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recovery history retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me/history")
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
    @Operation(
            summary = "Update Recovery Record",
            description = "Updates an existing recovery record.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recovery record updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recovery record not found")
    })
    @PutMapping("/{id}")
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
    @Operation(
            summary = "Delete Recovery Record",
            description = "Soft deletes a recovery record.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Recovery record deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recovery record not found")
    })
    @DeleteMapping("/{id}")
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
}