package com.mamahealth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mamahealth.dto.admin.ActivityResponse;
import com.mamahealth.dto.admin.AdminDashboardResponse;
import com.mamahealth.dto.admin.ReportResponse;
import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.service.AdminService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;

    }

    /**
     * Admin Dashboard Statistics
     */
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminDashboardResponse>> getDashboard() {

        return ResponseEntity.ok(

                ApiResponse.success(

                        "Admin dashboard retrieved successfully.",

                        adminService.getDashboard()

                )

        );

    }

    @GetMapping("/doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctors() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Doctors retrieved successfully.",

                    adminService.getAllDoctors()

            )

    );

    }

    @GetMapping("/doctors/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<DoctorResponse>> getDoctor(
        @PathVariable Long id) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Doctor retrieved successfully.",

                    adminService.getDoctor(id)

            )

    );

}

@PutMapping("/doctors/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
        @PathVariable Long id,
        @Valid @RequestBody CreateDoctorRequest request) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Doctor updated successfully.",

                    adminService.updateDoctor(id, request)

            )

    );

}

@PatchMapping("/doctors/{id}/deactivate")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<Void>> deactivateDoctor(
        @PathVariable Long id) {

    adminService.deactivateDoctor(id);

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Doctor deactivated successfully.",

                    null

            )

    );

}

@DeleteMapping("/doctors/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<Void>> deleteDoctor(
        @PathVariable Long id) {

    adminService.deleteDoctor(id);

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Doctor deleted successfully.",

                    null

            )

    );

}

@GetMapping("/mothers")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<List<MotherResponse>>> getAllMothers() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mothers retrieved successfully.",

                    adminService.getAllMothers()

            )

    );

}

@GetMapping("/reports")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<ReportResponse>> getReports() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Reports retrieved successfully.",

                    adminService.getReports()

            )

    );

}

@GetMapping("/activities")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<List<ActivityResponse>>> getActivities() {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Activities retrieved successfully.",

                    adminService.getRecentActivities()

            )

    );

}

@GetMapping("/mothers/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<MotherResponse>> getMother(
        @PathVariable Long id) {

    return ResponseEntity.ok(

            ApiResponse.success(

                    "Mother retrieved successfully.",

                    adminService.getMother(id)

            )

    );

}

}