package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.entity.Mother;

@Component
public class MotherMapper {

    public MotherResponse toResponse(Mother mother) {

        MotherResponse response = new MotherResponse();

        response.setId(mother.getId());
        response.setFullName(mother.getFullName());
        response.setPhoneNumber(mother.getPhoneNumber());
        response.setDateOfBirth(mother.getDateOfBirth());
        response.setAddress(mother.getAddress());
        response.setBloodGroup(mother.getBloodGroup());
        response.setEmergencyContact(mother.getEmergencyContact());
        response.setDeliveryDate(mother.getDeliveryDate());
        response.setHospitalName(mother.getHospitalName());

        if (mother.getUser() != null) {
            response.setEmail(mother.getUser().getEmail());
        }

        return response;
    }
}