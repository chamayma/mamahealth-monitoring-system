package com.mamahealth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Appointment;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Mother;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByMotherAndActiveTrueOrderByAppointmentDateAscAppointmentTimeAsc(
            Mother mother);

    List<Appointment> findByDoctorAndActiveTrueOrderByAppointmentDateAscAppointmentTimeAsc(
            Doctor doctor);

    Optional<Appointment> findByIdAndActiveTrue(Long id);
}