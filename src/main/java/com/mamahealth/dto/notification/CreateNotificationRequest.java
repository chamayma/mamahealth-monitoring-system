package com.mamahealth.dto.notification;

import com.mamahealth.entity.NotificationType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateNotificationRequest {

    @NotNull(message = "Mother ID is required")
    private Long motherId;

    @NotBlank(message = "Title is required")
    @Size(max = 255)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Title must be valid text")
    private String title;

    @NotBlank(message = "Message is required")
    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Message must be valid text")
    private String message;

    @NotNull(message = "Notification type is required")
    private NotificationType type;

}