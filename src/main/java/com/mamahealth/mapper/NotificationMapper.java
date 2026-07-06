package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.notification.NotificationResponse;
import com.mamahealth.entity.Notification;

@Component
public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {

        NotificationResponse response = new NotificationResponse();

        response.setId(notification.getId());

        if (notification.getMother() != null) {
            response.setMotherId(notification.getMother().getId());
        }

        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setIsRead(notification.getIsRead());
        response.setCreatedAt(notification.getCreatedAt());

        return response;
    }
}