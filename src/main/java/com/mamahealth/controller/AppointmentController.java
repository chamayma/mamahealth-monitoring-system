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
    public ResponseEntity<AppointmentResponse> createAppointment(
            @Valid @RequestBody CreateAppointmentRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        AppointmentResponse response =
                appointmentService.createAppointment(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Mother views appointments
     */
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<AppointmentResponse> response =
                appointmentService.getMyAppointments(
                        userDetails.getUsername());

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor updates appointment
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody CreateAppointmentRequest request) {

        AppointmentResponse response =
                appointmentService.updateAppointment(id, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor marks appointment completed
     */
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponse> markCompleted(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.markCompleted(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor cancels appointment
     */
    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable Long id) {

        AppointmentResponse response =
                appointmentService.cancelAppointment(id);

        return ResponseEntity.ok(response);
    }

    /**
     * Doctor deletes appointment
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok("Appointment deleted successfully.");
    }
}