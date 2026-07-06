package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Medication;
import com.mamahealth.entity.Mother;

public interface MedicationRepository
        extends JpaRepository<Medication, Long> {

    List<Medication> findByMotherAndActiveTrueOrderByStartDateDesc(
            Mother mother);

    Optional<Medication> findByIdAndActiveTrue(Long id);
}