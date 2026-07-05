package com.mamahealth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<MotherResponse> createProfile(
            @Valid @RequestBody CreateMotherRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        MotherResponse response = motherService.createMotherProfile(
                request,
                userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<MotherResponse> getMyProfile(
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    MotherResponse response =
            motherService.getMyProfile(userDetails.getUsername());

    return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
public ResponseEntity<MotherResponse> updateMyProfile(

        Authentication authentication,

        @Valid @RequestBody CreateMotherRequest request) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    MotherResponse response =
            motherService.updateMyProfile(
                    userDetails.getUsername(),
                    request);

    return ResponseEntity.ok(response);
    }

    @DeleteMapping("/me")
public ResponseEntity<String> deleteMyProfile(
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    motherService.deleteMyProfile(userDetails.getUsername());

    return ResponseEntity.ok("Mother profile deleted successfully.");
    }
}