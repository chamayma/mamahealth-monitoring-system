package com.mamahealth.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.appointment.AppointmentResponse;
import com.mamahealth.dto.appointment.CreateAppointmentRequest;
import com.mamahealth.entity.Appointment;
import com.mamahealth.entity.AppointmentStatus;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.User;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.AppointmentMapper;
import com.mamahealth.repository.AppointmentRepository;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class AppointmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final MotherRepository motherRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            MotherRepository motherRepository,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            AppointmentMapper appointmentMapper) {

        this.appointmentRepository = appointmentRepository;
        this.motherRepository = motherRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.appointmentMapper = appointmentMapper;
    }

    /**
     * Doctor schedules appointment
     */
    public AppointmentResponse createAppointment(
            CreateAppointmentRequest request,
            String doctorEmail) {

        User user = userRepository.findByEmail(doctorEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor user not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor profile not found"));

        Mother mother = motherRepository.findById(request.getMotherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother not found"));

        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setMother(mother);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setPurpose(request.getPurpose());
        appointment.setNotes(request.getNotes());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = appointmentRepository.save(appointment);

        logger.info("Doctor {} scheduled appointment {}",
                doctor.getId(),
                saved.getId());

        return appointmentMapper.toResponse(saved);
    }

    /**
     * Mother views appointments
     */
    public List<AppointmentResponse> getMyAppointments(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return appointmentRepository
                .findByMotherAndActiveTrueOrderByAppointmentDateAscAppointmentTimeAsc(mother)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    /**
     * Doctor updates appointment
     */
    public AppointmentResponse updateAppointment(
            Long appointmentId,
            CreateAppointmentRequest request) {

        Appointment appointment = appointmentRepository
                .findByIdAndActiveTrue(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        Mother mother = motherRepository.findById(request.getMotherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother not found"));

        appointment.setMother(mother);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setPurpose(request.getPurpose());
        appointment.setNotes(request.getNotes());

        Appointment updated = appointmentRepository.save(appointment);

        logger.info("Appointment {} updated", appointmentId);

        return appointmentMapper.toResponse(updated);
    }

    /**
     * Mark appointment completed
     */
    public AppointmentResponse markCompleted(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findByIdAndActiveTrue(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.COMPLETED);

        Appointment updated = appointmentRepository.save(appointment);

        logger.info("Appointment {} completed", appointmentId);

        return appointmentMapper.toResponse(updated);
    }

    /**
     * Cancel appointment
     */
    public AppointmentResponse cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findByIdAndActiveTrue(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);

        Appointment updated = appointmentRepository.save(appointment);

        logger.info("Appointment {} cancelled", appointmentId);

        return appointmentMapper.toResponse(updated);
    }

    /**
     * Soft delete appointment
     */
    public void deleteAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findByIdAndActiveTrue(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        appointment.setActive(false);

        appointmentRepository.save(appointment);

        logger.info("Appointment {} deleted", appointmentId);
    }
}