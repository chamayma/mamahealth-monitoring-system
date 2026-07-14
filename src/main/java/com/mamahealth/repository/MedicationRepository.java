package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Medication;
import com.mamahealth.entity.MedicationStatus;
import com.mamahealth.entity.Mother;

public interface MedicationRepository
        extends JpaRepository<Medication, Long> {

    List<Medication> findByMotherAndActiveTrueOrderByStartDateDesc(
            Mother mother);

    Optional<Medication> findByIdAndActiveTrue(Long id);
    long countByActiveTrue();

//     List<Medication>
// findTop5ByActiveTrueOrderByCreatedAtDesc();
long countByStatusAndActiveTrue(
            MedicationStatus status);

    List<Medication> findTop5ByStatusAndActiveTrueOrderByCompletedAtDesc(
            MedicationStatus status);

            List<Medication> findByMotherAndActiveTrueOrderByCreatedAtDesc(
        Mother mother);

        List<Medication> findByDoctorAndActiveTrueOrderByCreatedAtDesc(
        Doctor doctor);
        boolean existsByDoctor(Doctor doctor);
}