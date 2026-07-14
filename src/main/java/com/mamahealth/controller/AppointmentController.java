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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/appointments")
@Validated
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Doctor schedules appointment
     */
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
 * Mother confirms appointment attendance
 */
@PatchMapping("/{id}/confirm")
@PreAuthorize("hasRole('MOTHER')")
public ResponseEntity<ApiResponse<AppointmentResponse>> confirmAppointment(
        @PathVariable Long id,
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    AppointmentResponse response =
            appointmentService.confirmAppointment(
                    id,
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Appointment confirmed successfully.",
                    response));

}

    /**
     * Doctor views their appointments
     */
    @GetMapping("/doctor/me")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getDoctorAppointments(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<AppointmentResponse> response =
                appointmentService.getDoctorAppointments(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments retrieved successfully.",
                        response));
    }
     
    /**
 * Doctor views today's appointments
 */
@GetMapping("/today")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getTodayAppointments() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Today's appointments retrieved successfully.",

                    appointmentService.getTodayAppointments()

            )

    );

}

    /**
     * Doctor updates appointment
     */
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
 * Doctor marks appointment as missed
 */
@PatchMapping("/{id}/missed")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<AppointmentResponse>> markMissed(
        @PathVariable Long id) {

    AppointmentResponse response =
            appointmentService.markMissed(id);

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Appointment marked as missed.",
                    response));

}

@GetMapping("/{id}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointment(
        @PathVariable Long id) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Appointment retrieved successfully.",

                    appointmentService.getAppointment(id)

            )

    );

}

    /**
     * Doctor cancels appointment
     */
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

    @GetMapping("/mother/{motherId}")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getMotherAppointments(
        @PathVariable Long motherId) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mother appointments retrieved successfully.",

                    appointmentService.getMotherAppointments(motherId)

            )

    );

}

/**
 * Get next appointment
 */
@GetMapping("/next")
@PreAuthorize("hasRole('MOTHER')")
public ResponseEntity<ApiResponse<AppointmentResponse>> getNextAppointment(
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    AppointmentResponse response =
            appointmentService.getNextAppointment(
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Next appointment retrieved successfully.",
                    response));
}

}