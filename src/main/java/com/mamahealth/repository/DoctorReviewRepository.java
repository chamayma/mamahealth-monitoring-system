package com.mamahealth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.DoctorReview;
import com.mamahealth.entity.RecoveryRecord;

public interface DoctorReviewRepository
        extends JpaRepository<DoctorReview, Long> {

    Optional<DoctorReview> findByRecoveryRecordAndActiveTrue(
            RecoveryRecord recoveryRecord);

    Optional<DoctorReview> findByIdAndActiveTrue(Long id);
}