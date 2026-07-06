package com.mamahealth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.doctorreview.CreateDoctorReviewRequest;
import com.mamahealth.dto.doctorreview.DoctorReviewResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.DoctorReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/doctor-reviews")
@Validated
@PreAuthorize("hasRole('DOCTOR')")
@Tag(
        name = "Doctor Review Management",
        description = "APIs for managing clinical reviews")
@SecurityRequirement(name = "Bearer Authentication")
public class DoctorReviewController {

    private final DoctorReviewService doctorReviewService;

    public DoctorReviewController(DoctorReviewService doctorReviewService) {
        this.doctorReviewService = doctorReviewService;
    }

    /**
     * Create Doctor Review
     */
    @Operation(
            summary = "Create Doctor Review",
            description = "Creates a clinical review for a recovery record.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Doctor review created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Recovery record not found")
    })
    @PostMapping("/{recoveryId}")
    public ResponseEntity<ApiResponse<DoctorReviewResponse>> createReview(
            @PathVariable Long recoveryId,
            @Valid @RequestBody CreateDoctorReviewRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorReviewResponse response =
                doctorReviewService.createReview(
                        recoveryId,
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor review created successfully.",
                        response));
    }

    /**
     * Get Doctor Review
     */
    @Operation(
            summary = "Get Doctor Review",
            description = "Returns the clinical review for a recovery record.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Doctor review retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor review not found")
    })
    @GetMapping("/{recoveryId}")
    public ResponseEntity<ApiResponse<DoctorReviewResponse>> getReview(
            @PathVariable Long recoveryId) {

        DoctorReviewResponse response =
                doctorReviewService.getReview(recoveryId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor review retrieved successfully.",
                        response));
    }

    /**
     * Update Doctor Review
     */
    @Operation(
            summary = "Update Doctor Review",
            description = "Updates an existing clinical review.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Doctor review updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor review not found")
    })
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse<DoctorReviewResponse>> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody CreateDoctorReviewRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        DoctorReviewResponse response =
                doctorReviewService.updateReview(
                        reviewId,
                        request,
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor review updated successfully.",
                        response));
    }

    /**
     * Delete Doctor Review
     */
    @Operation(
            summary = "Delete Doctor Review",
            description = "Soft deletes a clinical review.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Doctor review deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Doctor review not found")
    })
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long reviewId,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        doctorReviewService.deleteReview(
                reviewId,
                userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor review deleted successfully.",
                        null));
    }
}
