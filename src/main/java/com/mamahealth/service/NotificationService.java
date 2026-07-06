package com.mamahealth.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.notification.CreateNotificationRequest;
import com.mamahealth.dto.notification.NotificationResponse;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.Notification;
import com.mamahealth.entity.User;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.NotificationMapper;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.NotificationRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class NotificationService {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final MotherRepository motherRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(
            NotificationRepository notificationRepository,
            MotherRepository motherRepository,
            UserRepository userRepository,
            NotificationMapper notificationMapper) {

        this.notificationRepository = notificationRepository;
        this.motherRepository = motherRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    /**
     * Create Notification
     */
    public NotificationResponse createNotification(
            CreateNotificationRequest request) {

        Mother mother = motherRepository.findById(request.getMotherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother not found"));

        Notification notification = new Notification();

        notification.setMother(mother);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());

        Notification saved = notificationRepository.save(notification);

        logger.info("Notification created for Mother {}", mother.getId());

        return notificationMapper.toResponse(saved);
    }

    /**
     * Mother views all notifications
     */
    public List<NotificationResponse> getMyNotifications(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return notificationRepository
                .findByMotherAndActiveTrueOrderByCreatedAtDesc(mother)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    /**
     * Mother views unread notifications
     */
    public List<NotificationResponse> getUnreadNotifications(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return notificationRepository
                .findByMotherAndIsReadFalseAndActiveTrueOrderByCreatedAtDesc(mother)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    /**
     * Mark notification as read
     */
    public NotificationResponse markAsRead(
            Long notificationId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        Notification notification = notificationRepository
                .findByIdAndActiveTrue(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found"));

        if (!notification.getMother().getId().equals(mother.getId())) {
            throw new ResourceNotFoundException(
                    "You cannot modify another mother's notification.");
        }

        notification.setIsRead(true);

        Notification updated = notificationRepository.save(notification);

        logger.info("Mother {} read notification {}",
                mother.getId(),
                notificationId);

        return notificationMapper.toResponse(updated);
    }

    /**
     * Soft delete notification
     */
    public void deleteNotification(
            Long notificationId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        Notification notification = notificationRepository
                .findByIdAndActiveTrue(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Notification not found"));

        if (!notification.getMother().getId().equals(mother.getId())) {
            throw new ResourceNotFoundException(
                    "You cannot delete another mother's notification.");
        }

        notification.setActive(false);

        notificationRepository.save(notification);

        logger.info("Mother {} deleted notification {}",
                mother.getId(),
                notificationId);
    }
}