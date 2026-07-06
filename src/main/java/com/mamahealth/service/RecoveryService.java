package com.mamahealth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mamahealth.dto.recovery.CreateRecoveryRequest;
import com.mamahealth.dto.recovery.RecoveryResponse;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.RecoveryRecord;
import com.mamahealth.entity.User;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.RecoveryMapper;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.RecoveryRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class RecoveryService {

    private final RecoveryRepository recoveryRepository;
    private final MotherRepository motherRepository;
    private final UserRepository userRepository;
    private final RecoveryMapper recoveryMapper;

    public RecoveryService(
            RecoveryRepository recoveryRepository,
            MotherRepository motherRepository,
            UserRepository userRepository,
            RecoveryMapper recoveryMapper) {

        this.recoveryRepository = recoveryRepository;
        this.motherRepository = motherRepository;
        this.userRepository = userRepository;
        this.recoveryMapper = recoveryMapper;
    }

    /**
     * Create Recovery Record
     */
    public RecoveryResponse createRecoveryRecord(
            CreateRecoveryRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        RecoveryRecord record = new RecoveryRecord();

        record.setMother(mother);
        record.setRecordDate(request.getRecordDate());
        record.setPainLevel(request.getPainLevel());
        record.setBodyTemperature(request.getBodyTemperature());
        record.setWoundCondition(request.getWoundCondition());
        record.setBleedingLevel(request.getBleedingLevel());
        record.setMobility(request.getMobility());
        record.setMedicationTaken(request.getMedicationTaken());
        record.setNotes(request.getNotes());

        RecoveryRecord saved = recoveryRepository.save(record);

        return recoveryMapper.toResponse(saved);
    }

    /**
     * Get My Recovery History
     */
    public List<RecoveryResponse> getMyRecoveryHistory(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return recoveryRepository
                .findByMotherAndActiveTrueOrderByRecordDateDesc(mother)
                .stream()
                .map(recoveryMapper::toResponse)
                .toList();
    }

    /**
     * Update Recovery Record
     */
    public RecoveryResponse updateRecoveryRecord(
            Long id,
            CreateRecoveryRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        RecoveryRecord record = recoveryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recovery record not found"));

        if (!record.getMother().getId().equals(mother.getId())) {
            throw new ResourceNotFoundException("You cannot update this recovery record");
        }

        record.setRecordDate(request.getRecordDate());
        record.setPainLevel(request.getPainLevel());
        record.setBodyTemperature(request.getBodyTemperature());
        record.setWoundCondition(request.getWoundCondition());
        record.setBleedingLevel(request.getBleedingLevel());
        record.setMobility(request.getMobility());
        record.setMedicationTaken(request.getMedicationTaken());
        record.setNotes(request.getNotes());

        RecoveryRecord updated = recoveryRepository.save(record);

        return recoveryMapper.toResponse(updated);
    }

    /**
     * Soft Delete Recovery Record
     */
    public void deleteRecoveryRecord(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        RecoveryRecord record = recoveryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recovery record not found"));

        if (!record.getMother().getId().equals(mother.getId())) {
            throw new ResourceNotFoundException("You cannot delete this recovery record");
        }

        record.setActive(false);

        recoveryRepository.save(record);
    }
}