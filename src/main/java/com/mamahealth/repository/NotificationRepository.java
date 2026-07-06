package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Mother;
import com.mamahealth.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByMotherAndActiveTrueOrderByCreatedAtDesc(
            Mother mother);

    List<Notification> findByMotherAndIsReadFalseAndActiveTrueOrderByCreatedAtDesc(
            Mother mother);

    Optional<Notification> findByIdAndActiveTrue(Long id);
}