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

    response.setMotherId(
            notification.getMother().getId());

    response.setMotherName(
            notification.getMother().getFullName());

}

        if (notification.getDoctor() != null) {

             response.setDoctorId(
            notification.getDoctor().getId());

    response.setDoctorName(
            notification.getDoctor().getFullName());


        }

        response.setTitle(notification.getTitle());
        response.setMessage(notification.getMessage());
        response.setType(notification.getType());
        response.setCreatedAt(notification.getCreatedAt());
        response.setStatus(notification.getStatus());
        response.setReadAt(notification.getReadAt());

        return response;
    }
}