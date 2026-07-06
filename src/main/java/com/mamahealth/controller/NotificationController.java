package com.mamahealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.mamahealth.dto.common.ApiResponse;
import com.mamahealth.dto.notification.CreateNotificationRequest;
import com.mamahealth.dto.notification.NotificationResponse;
import com.mamahealth.security.CustomUserDetails;
import com.mamahealth.service.NotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
@Validated
@Tag(
        name = "Notification Management",
        description = "APIs for managing notifications")
@SecurityRequirement(name = "Bearer Authentication")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Create Notification
     */
    @Operation(
            summary = "Create Notification",
            description = "Allows an Admin or Doctor to create a notification for a mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Notification created successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Validation failed"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Mother not found")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {

        NotificationResponse response =
                notificationService.createNotification(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Notification created successfully.",
                        response));
    }

    /**
     * Get My Notifications
     */
    @Operation(
            summary = "Get My Notifications",
            description = "Returns all notifications for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notifications retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMyNotifications(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<NotificationResponse> response =
                notificationService.getMyNotifications(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully.",
                        response));
    }

    /**
     * Get Unread Notifications
     */
    @Operation(
            summary = "Get Unread Notifications",
            description = "Returns unread notifications for the authenticated mother.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Unread notifications retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me/unread")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUnreadNotifications(
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        List<NotificationResponse> response =
                notificationService.getUnreadNotifications(
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Unread notifications retrieved successfully.",
                        response));
    }

    /**
     * Mark Notification as Read
     */
    @Operation(
            summary = "Mark Notification as Read",
            description = "Marks a notification as read.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notification marked as read"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PatchMapping("/{id}/read")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<NotificationResponse>> markAsRead(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        NotificationResponse response =
                notificationService.markAsRead(
                        id,
                        userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification marked as read.",
                        response));
    }

    /**
     * Delete Notification
     */
    @Operation(
            summary = "Delete Notification",
            description = "Soft deletes a notification.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notification deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MOTHER')")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long id,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        notificationService.deleteNotification(
                id,
                userDetails.getUsername());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully.",
                        null));
    }
}
