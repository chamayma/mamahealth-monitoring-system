package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Mother;
import com.mamahealth.entity.RecoveryRecord;

public interface RecoveryRepository
        extends JpaRepository<RecoveryRecord, Long> {

    List<RecoveryRecord> findByMotherAndActiveTrueOrderByRecordDateDesc(
            Mother mother);

    Optional<RecoveryRecord> findByIdAndActiveTrue(Long id);
    long countByActiveTrue();

    List<RecoveryRecord> findTop5ByActiveTrueOrderByCreatedAtDesc();
}