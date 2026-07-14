package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.Notification;
import com.mamahealth.entity.NotificationStatus;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByMotherAndActiveTrueOrderByCreatedAtDesc(
            Mother mother);
        List<Notification> findByMotherAndStatusAndActiveTrueOrderByCreatedAtDesc(
        Mother mother,
        NotificationStatus status);
    Optional<Notification> findByIdAndActiveTrue(Long id);

    List<Notification>
findTop5ByActiveTrueOrderByCreatedAtDesc();
List<Notification> findByDoctorAndActiveTrueOrderByCreatedAtDesc(
        Doctor doctor);
    long countByActiveTrue();

    boolean existsByDoctor(Doctor doctor);

}