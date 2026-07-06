package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.medication.MedicationResponse;
import com.mamahealth.entity.Medication;

@Component
public class MedicationMapper {

    public MedicationResponse toResponse(Medication medication) {

        MedicationResponse response = new MedicationResponse();

        response.setId(medication.getId());

        if (medication.getMother() != null) {
            response.setMotherId(medication.getMother().getId());
        }

        if (medication.getDoctor() != null) {
            response.setDoctorId(medication.getDoctor().getId());
        }

        response.setMedicationName(medication.getMedicationName());
        response.setDosage(medication.getDosage());
        response.setFrequency(medication.getFrequency());
        response.setStartDate(medication.getStartDate());
        response.setEndDate(medication.getEndDate());
        response.setInstructions(medication.getInstructions());
        response.setStatus(medication.getStatus());
        response.setCreatedAt(medication.getCreatedAt());

        return response;
    }
}