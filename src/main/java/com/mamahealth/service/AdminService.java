package com.mamahealth.service;

import org.springframework.stereotype.Service;

import com.mamahealth.dto.admin.AdminDashboardResponse;
import com.mamahealth.repository.AppointmentRepository;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.MedicationRepository;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.RecoveryRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final MotherRepository motherRepository;
    private final RecoveryRepository recoveryRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicationRepository medicationRepository;

    public AdminService(
            UserRepository userRepository,
            DoctorRepository doctorRepository,
            MotherRepository motherRepository,
            RecoveryRepository recoveryRepository,
            AppointmentRepository appointmentRepository,
            MedicationRepository medicationRepository) {

        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.motherRepository = motherRepository;
        this.recoveryRepository = recoveryRepository;
        this.appointmentRepository = appointmentRepository;
        this.medicationRepository = medicationRepository;
    }

    public AdminDashboardResponse getDashboard() {

        AdminDashboardResponse response = new AdminDashboardResponse();

        response.setTotalUsers(userRepository.count());
        response.setTotalDoctors(doctorRepository.count());
        response.setTotalMothers(motherRepository.count());
        response.setTotalRecoveryRecords(recoveryRepository.count());
        response.setTotalAppointments(appointmentRepository.count());
        response.setTotalMedications(medicationRepository.count());

        return response;
    }
}