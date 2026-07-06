package com.mamahealth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.doctor.CreateDoctorRequest;
import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.User;
import com.mamahealth.mapper.DoctorMapper;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class DoctorService {

    private static final Logger logger =
            LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;

    public DoctorService(
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            DoctorMapper doctorMapper) {

        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.doctorMapper = doctorMapper;
    }

    /**
     * Create Doctor Profile
     */
    public DoctorResponse createDoctorProfile(
            CreateDoctorRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (doctorRepository.findByUserAndActiveTrue(user).isPresent()) {
            throw new RuntimeException("Doctor profile already exists");
        }

        if (doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new RuntimeException("License number already exists");
        }

        Doctor doctor = new Doctor();

        doctor.setFullName(request.getFullName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setYearsOfExperience(request.getYearsOfExperience());

        doctor.setActive(true);
        doctor.setUser(user);

        Doctor savedDoctor = doctorRepository.save(doctor);

        logger.info("Doctor profile created for {}", email);

        return doctorMapper.toResponse(savedDoctor);
    }

    /**
     * Get Logged-in Doctor Profile
     */
    public DoctorResponse getMyProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new RuntimeException("Doctor profile not found"));

        return doctorMapper.toResponse(doctor);
    }

    /**
     * Update Doctor Profile
     */
    public DoctorResponse updateMyProfile(
            String email,
            CreateDoctorRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new RuntimeException("Doctor profile not found"));

        doctor.setFullName(request.getFullName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setYearsOfExperience(request.getYearsOfExperience());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        logger.info("Doctor profile updated for {}", email);

        return doctorMapper.toResponse(updatedDoctor);
    }

    /**
     * Soft Delete Doctor Profile
     */
    public void deleteMyProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new RuntimeException("Doctor profile not found"));

        doctor.setActive(false);

        doctorRepository.save(doctor);

        logger.info("Doctor profile deleted for {}", email);
    }
}