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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/doctor-reviews")
@Validated
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorReviewController {

    private final DoctorReviewService doctorReviewService;

    public DoctorReviewController(DoctorReviewService doctorReviewService) {
        this.doctorReviewService = doctorReviewService;
    }

    /**
     * Create Doctor Review
     */
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
