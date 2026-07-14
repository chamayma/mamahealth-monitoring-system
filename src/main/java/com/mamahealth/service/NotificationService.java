package com.mamahealth.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.notification.CreateNotificationRequest;
import com.mamahealth.dto.notification.NotificationResponse;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.Notification;
import com.mamahealth.entity.NotificationStatus;
import com.mamahealth.entity.User;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.NotificationMapper;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.NotificationRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class NotificationService {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final MotherRepository motherRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(
            NotificationRepository notificationRepository,
            MotherRepository motherRepository,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            NotificationMapper notificationMapper) {

        this.notificationRepository = notificationRepository;
        this.motherRepository = motherRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.notificationMapper = notificationMapper;
    }

    /**
     * Doctor creates notification
     */
    public NotificationResponse createNotification(
            CreateNotificationRequest request,
            String doctorEmail) {

        User user = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor profile not found"));

        Mother mother = motherRepository.findById(request.getMotherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother not found"));

        Notification notification = new Notification();

        notification.setDoctor(doctor);
        notification.setMother(mother);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());

        Notification saved = notificationRepository.save(notification);

        logger.info(
                "Doctor {} sent notification {} to Mother {}",
                doctor.getId(),
                saved.getId(),
                mother.getId());

        return notificationMapper.toResponse(saved);
    }

    /**
     * Mother views all notifications
     */
    public List<NotificationResponse> getMyNotifications(String email) {

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
    public List<NotificationResponse> getUnreadNotifications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return notificationRepository
                .findByMotherAndStatusAndActiveTrueOrderByCreatedAtDesc(
                        mother,
                        NotificationStatus.UNREAD)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    /**
     * Mother marks notification as read
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

        if (notification.getStatus() == NotificationStatus.READ) {
            return notificationMapper.toResponse(notification);
        }

        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());

        Notification updated = notificationRepository.save(notification);

        logger.info(
                "Mother {} read notification {}",
                mother.getId(),
                notificationId);

        return notificationMapper.toResponse(updated);
    }

   /**
 * Doctor deletes notification
 */
public void deleteNotification(
        Long notificationId,
        String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor profile not found"));

    Notification notification = notificationRepository
            .findByIdAndActiveTrue(notificationId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Notification not found"));

    if (notification.getDoctor() == null) {

        throw new ResourceNotFoundException(
                "Notification has no doctor assigned.");

    }

    if (!notification.getDoctor().getId().equals(doctor.getId())) {

        throw new ResourceNotFoundException(
                "You cannot delete another doctor's notification.");

    }

    notification.setActive(false);

    notificationRepository.save(notification);

    logger.info(
            "Doctor {} deleted notification {}",
            doctor.getId(),
            notificationId);

}

    /**
     * Doctor dashboard recent notifications
     */
    public List<NotificationResponse> getRecentNotifications() {

        return notificationRepository
                .findTop5ByActiveTrueOrderByCreatedAtDesc()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    /**
     * Doctor views notifications for one mother
     */
    public List<NotificationResponse> getMotherNotifications(Long motherId) {

        Mother mother = motherRepository.findById(motherId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother not found"));

        return notificationRepository
                .findByMotherAndActiveTrueOrderByCreatedAtDesc(mother)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    /**
 * Doctor views all notifications they have sent
 */
public List<NotificationResponse> getDoctorNotifications(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor profile not found"));

    return notificationRepository
            .findByDoctorAndActiveTrueOrderByCreatedAtDesc(doctor)
            .stream()
            .map(notificationMapper::toResponse)
            .toList();

}
}