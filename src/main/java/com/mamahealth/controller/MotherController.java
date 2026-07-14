package com.mamahealth.controller;

import java.util.List;

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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mothers")
@Validated
public class MotherController {

    private final MotherService motherService;

    public MotherController(MotherService motherService) {
        this.motherService = motherService;
    }

    @PostMapping
    @PreAuthorize("hasRole('MOTHER')")
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

    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
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

    @PutMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
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

    @DeleteMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
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

    @GetMapping("/all")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<MotherResponse>>> getAllMothers() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mothers retrieved successfully.",

                    motherService.getAllMothers()

            )

    );

}

@GetMapping("/{id}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<MotherResponse>> getMotherById(
        @PathVariable Long id) {

    MotherResponse response =
            motherService.getMotherById(id);

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mother retrieved successfully.",

                    response

            )

    );

}
}