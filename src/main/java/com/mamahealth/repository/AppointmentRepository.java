package com.mamahealth.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mamahealth.entity.Appointment;
import com.mamahealth.entity.AppointmentStatus;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Mother;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByMotherAndActiveTrueOrderByAppointmentDateAscAppointmentTimeAsc(
            Mother mother);

    List<Appointment> findByDoctorAndActiveTrueOrderByAppointmentDateAscAppointmentTimeAsc(
            Doctor doctor);

    Optional<Appointment> findByIdAndActiveTrue(Long id);
    long countByActiveTrue();

    List<Appointment>
findTop5ByAppointmentDateAndActiveTrueOrderByAppointmentTimeAsc(
        LocalDate appointmentDate);

   List<Appointment> findByMotherAndActiveTrueOrderByAppointmentDateDescAppointmentTimeDesc(
        Mother mother); 
        
        Optional<Appointment> findFirstByMotherAndStatusAndAppointmentDateGreaterThanEqualOrderByAppointmentDateAscAppointmentTimeAsc(
        Mother mother,
        AppointmentStatus status,
        LocalDate date);
        boolean existsByDoctor(Doctor doctor);

        long countByStatus(AppointmentStatus completed);
}