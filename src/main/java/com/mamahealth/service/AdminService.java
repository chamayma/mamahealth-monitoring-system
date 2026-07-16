package com.mamahealth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mamahealth.dto.admin.ActivityResponse;
import com.mamahealth.dto.admin.AdminDashboardResponse;
import com.mamahealth.dto.admin.ReportResponse;
import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.entity.AppointmentStatus;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.MedicationStatus;
import com.mamahealth.entity.Mother;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.repository.AppointmentRepository;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.MedicationRepository;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.NotificationRepository;
import com.mamahealth.repository.RecoveryRepository;
import com.mamahealth.repository.UserRepository;
import com.mamahealth.mapper.DoctorMapper;
import com.mamahealth.mapper.MotherMapper;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final MotherRepository motherRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicationRepository medicationRepository;
    private final RecoveryRepository recoveryRepository;
    private final NotificationRepository notificationRepository;
    private final DoctorMapper doctorMapper;
    private final MotherMapper motherMapper;

    public AdminService(
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            MotherRepository motherRepository,
            AppointmentRepository appointmentRepository,
            MedicationRepository medicationRepository,
            RecoveryRepository recoveryRepository,
            NotificationRepository notificationRepository,
            DoctorMapper doctorMapper,
            MotherMapper motherMapper) {

        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.motherRepository = motherRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicationRepository = medicationRepository;
        this.recoveryRepository = recoveryRepository;
        this.notificationRepository = notificationRepository;
        this.doctorMapper = doctorMapper;
        this.motherMapper = motherMapper;
    }

    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response =
                new AdminDashboardResponse();

        response.setTotalUsers(
                userRepository.count());

        response.setTotalDoctors(
                doctorRepository.countByActiveTrue());

        response.setTotalMothers(
                motherRepository.countByActiveTrue());

        response.setTotalAppointments(
                appointmentRepository.countByActiveTrue());

        response.setTotalMedications(
                medicationRepository.countByActiveTrue());

        response.setTotalRecoveries(
                recoveryRepository.countByActiveTrue());

        response.setTotalNotifications(
                notificationRepository.countByActiveTrue());

        return response;
    }

    public List<DoctorResponse> getAllDoctors() {

    return doctorRepository
        .findByActiveTrueOrderByIdDesc()
        .stream()
        .map(doctorMapper::toResponse)
        .toList();
    }

    public DoctorResponse getDoctor(Long id) {

    Doctor doctor = doctorRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor not found"));

    return doctorMapper.toResponse(doctor);

}

public DoctorResponse updateDoctor(
        Long id,
        CreateDoctorRequest request) {

    Doctor doctor = doctorRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor not found"));

    doctor.setFullName(request.getFullName());
    doctor.setPhoneNumber(request.getPhoneNumber());
    doctor.setHospitalName(request.getHospitalName());
    doctor.setSpecialization(request.getSpecialization());

    Doctor updated =
            doctorRepository.save(doctor);

    return doctorMapper.toResponse(updated);

}

public void deactivateDoctor(Long id) {

    Doctor doctor = doctorRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor not found"));

    doctor.setActive(false);

    doctorRepository.save(doctor);

}

public void deleteDoctor(Long id) {

    Doctor doctor = doctorRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor not found"));

    if (appointmentRepository.existsByDoctor(doctor)
            || medicationRepository.existsByDoctor(doctor)
            || notificationRepository.existsByDoctor(doctor)) {

        throw new IllegalStateException(
                "Doctor cannot be deleted because medical records exist.");

    }

    doctorRepository.delete(doctor);

}

public List<MotherResponse> getAllMothers() {

    return motherRepository
            .findByActiveTrueOrderByIdDesc()
            .stream()
            .map(motherMapper::toResponse)
            .toList();

}

public ReportResponse getReports() {

    ReportResponse response =
            new ReportResponse();

    response.setTotalDoctors(
            doctorRepository.countByActiveTrue());

    response.setTotalMothers(
            motherRepository.countByActiveTrue());

    response.setTotalAppointments(
            appointmentRepository.countByActiveTrue());

    response.setCompletedAppointments(
            appointmentRepository.countByStatus(
                    AppointmentStatus.COMPLETED));

    response.setMissedAppointments(
            appointmentRepository.countByStatus(
                    AppointmentStatus.MISSED));

    response.setActiveMedications(
            medicationRepository.countByStatusAndActiveTrue(
                    MedicationStatus.ACTIVE));

    response.setCompletedMedications(
            medicationRepository.countByStatusAndActiveTrue(
                    MedicationStatus.COMPLETED));

    response.setRecoveryReports(
            recoveryRepository.countByActiveTrue());

    response.setNotifications(
            notificationRepository.countByActiveTrue());

    return response;

}

public List<ActivityResponse> getRecentActivities() {

    List<ActivityResponse> activities = new ArrayList<>();

    notificationRepository
            .findTop5ByActiveTrueOrderByCreatedAtDesc()
            .forEach(notification -> {

                ActivityResponse response =
                        new ActivityResponse();

                response.setActivity(
                        "Notification sent to "
                                + notification.getMother().getFullName());

                response.setPerformedBy(
                        notification.getDoctor().getFullName());

                response.setCreatedAt(
                        notification.getCreatedAt());

                activities.add(response);

            });

    return activities;

}

    public MotherResponse getMother(Long id) {

    Mother mother = motherRepository
            .findByIdAndActiveTrue(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Mother not found"));

    return motherMapper.toResponse(mother);

}

    public void promoteUser(String email, String roleStr) {
        com.mamahealth.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setRole(com.mamahealth.entity.Role.valueOf(roleStr.toUpperCase()));
        userRepository.save(user);
    }
}