package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.appointment.AppointmentResponse;
import com.mamahealth.dto.appointment.CreateAppointmentRequest;
import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.AppointmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/appointments")
@Validated
@Tag(
        name = "Appointment Management",
        description = "APIs for managing appointments")
@SecurityRequirement(name = "Bearer Authentication")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Doctor schedules appointment
     */
    @Operation(
            summary = "Create Appointment",
            description = "Allows a doctor to schedule an appointment for a mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Appointment created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother or Doctor not found")
    })
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        AppointmentResponse response =
                appointmentService.createAppointment(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Appointment created successfully.",
                        response));
    }

    /**
     * Mother views appointments
     */
    @Operation(
            summary = "Get My Appointments",
            description = "Returns all appointments for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getMyAppointments(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<AppointmentResponse> response =
                appointmentService.getMyAppointments(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments retrieved successfully.",
                        response));
    }

    /**
     * Doctor updates appointment
     */
    @Operation(
            summary = "Update Appointment",
            description = "Updates an existing appointment.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody CreateAppointmentRequest request) {

        AppointmentResponse response =
                appointmentService.updateAppointment(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment updated successfully.",
                        response));
    }

    /**
     * Doctor marks appointment completed
     */
    @Operation(
            summary = "Complete Appointment",
            description = "Marks an appointment as completed.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment completed successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<AppointmentResponse>> markCompleted(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.markCompleted(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment completed successfully.",
                        response));
    }

    /**
     * Doctor cancels appointment
     */
    @Operation(
            summary = "Cancel Appointment",
            description = "Cancels an appointment.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment cancelled successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<AppointmentResponse>> cancelAppointment(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.cancelAppointment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment cancelled successfully.",
                        response));
    }

    /**
     * Doctor deletes appointment
     */
    @Operation(
            summary = "Delete Appointment",
            description = "Soft deletes an appointment.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(
            @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment deleted successfully.",
                        null));
    }
}