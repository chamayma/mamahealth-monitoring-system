package com.mamahealth.dto.notification;

import java.time.LocalDateTime;

import com.mamahealth.entity.NotificationStatus;
import com.mamahealth.entity.NotificationType;

import lombok.Data;

@Data
public class NotificationResponse {

    private Long id;
    private Long motherId;
    private String motherName;
    private Long doctorId;
    private String doctorName;
    private String title;
    private String message;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private NotificationStatus status;
    private LocalDateTime readAt;

}