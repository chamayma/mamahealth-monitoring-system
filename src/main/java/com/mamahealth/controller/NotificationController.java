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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
@Validated
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Doctor creates notification
     */
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(
            @Valid @RequestBody CreateNotificationRequest request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        NotificationResponse response =
                notificationService.createNotification(
                        request,
                        userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Notification created successfully.",
                        response));
    }

    /**
     * Mother views all notifications
     */
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
     * Mother views unread notifications
     */
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
     * Mother marks notification as read
     */
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
 * Doctor deletes a notification
 */
@DeleteMapping("/{id}")
@PreAuthorize("hasRole('DOCTOR')")
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

    /**
     * Doctor dashboard recent notifications
     */
    @GetMapping("/recent")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getRecentNotifications() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Recent notifications retrieved successfully.",
                        notificationService.getRecentNotifications()));
    }

    /**
     * Doctor views notifications of one mother
     */
    @GetMapping("/mother/{motherId}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getMotherNotifications(
            @PathVariable Long motherId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mother notifications retrieved successfully.",
                        notificationService.getMotherNotifications(motherId)));
    }

    /**
 * Doctor views all notifications they have sent
 */
@GetMapping("/doctor/me")
@PreAuthorize("hasRole('DOCTOR')")
public ResponseEntity<ApiResponse<List<NotificationResponse>>> getDoctorNotifications(
        Authentication authentication) {

    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    List<NotificationResponse> response =
            notificationService.getDoctorNotifications(
                    userDetails.getUsername());

    return ResponseEntity.ok(
            ApiResponse.success(
                    "Doctor notifications retrieved successfully.",
                    response));

}
}