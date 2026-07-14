package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.User;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser(User user);

    Optional<Doctor> findByUserAndActiveTrue(User user);
    Optional<Doctor> findByIdAndActiveTrue(Long id);

    boolean existsByLicenseNumber(String licenseNumber);
    long countByActiveTrue();
    List<Doctor> findByActiveTrueOrderByIdDesc();
}