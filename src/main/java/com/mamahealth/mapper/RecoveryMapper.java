package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.recovery.RecoveryResponse;
import com.mamahealth.entity.RecoveryRecord;

@Component
public class RecoveryMapper {

    public RecoveryResponse toResponse(RecoveryRecord record) {

        RecoveryResponse response = new RecoveryResponse();

        response.setId(record.getId());

        if (record.getMother() != null) {
            response.setMotherName(
                    record.getMother().getFullName());
        }

        response.setRecordDate(record.getRecordDate());
        response.setPainLevel(record.getPainLevel());
        response.setBodyTemperature(record.getBodyTemperature());
        response.setWoundCondition(record.getWoundCondition());
        response.setBleedingLevel(record.getBleedingLevel());
        response.setMobility(record.getMobility());
        response.setMedicationTaken(record.getMedicationTaken());
        response.setNotes(record.getNotes());
        response.setCreatedAt(record.getCreatedAt());

        return response;
    }
}