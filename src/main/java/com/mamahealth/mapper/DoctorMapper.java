package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.doctor.DoctorResponse;
import com.mamahealth.entity.Doctor;

@Component
public class DoctorMapper {

    public DoctorResponse toResponse(Doctor doctor) {

        DoctorResponse response = new DoctorResponse();

        response.setId(doctor.getId());
        response.setFullName(doctor.getFullName());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setSpecialization(doctor.getSpecialization());
        response.setLicenseNumber(doctor.getLicenseNumber());
        response.setHospitalName(doctor.getHospitalName());
        response.setYearsOfExperience(doctor.getYearsOfExperience());

        if (doctor.getUser() != null) {
            response.setEmail(doctor.getUser().getEmail());
        }

        return response;
    }
}