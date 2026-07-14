package com.mamahealth.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.medication.CreateMedicationRequest;
import com.mamahealth.dto.medication.MedicationResponse;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.Medication;
import com.mamahealth.entity.MedicationStatus;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.User;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.MedicationMapper;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.MedicationRepository;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class MedicationService {

    private static final Logger logger =
            LoggerFactory.getLogger(MedicationService.class);

    private final MedicationRepository medicationRepository;
    private final MotherRepository motherRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final MedicationMapper medicationMapper;

    public MedicationService(
            MedicationRepository medicationRepository,
            MotherRepository motherRepository,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            MedicationMapper medicationMapper) {

        this.medicationRepository = medicationRepository;
        this.motherRepository = motherRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.medicationMapper = medicationMapper;
    }

    /**
     * Doctor prescribes medication
     */
    public MedicationResponse prescribeMedication(
            CreateMedicationRequest request,
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

        Medication medication = new Medication();

        medication.setDoctor(doctor);
        medication.setMother(mother);
        medication.setMedicationName(request.getMedicationName());
        medication.setDosage(request.getDosage());
        medication.setFrequency(request.getFrequency());
        medication.setStartDate(request.getStartDate());
        medication.setEndDate(request.getEndDate());
        medication.setInstructions(request.getInstructions());
        medication.setStatus(MedicationStatus.ACTIVE);

        Medication saved = medicationRepository.save(medication);

        logger.info("Doctor {} prescribed {} to Mother {}",
                doctor.getId(),
                saved.getMedicationName(),
                mother.getId());

        return medicationMapper.toResponse(saved);
    }

    /**
     * Mother views her medications
     */
    public List<MedicationResponse> getMyMedications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return medicationRepository
                .findByMotherAndActiveTrueOrderByStartDateDesc(mother)
                .stream()
                .map(medicationMapper::toResponse)
                .toList();
    }

    /**
     * Doctor updates prescription
     */
//     public MedicationResponse updateMedication(
//             Long medicationId,
//             CreateMedicationRequest request) {

//         Medication medication = medicationRepository
//                 .findByIdAndActiveTrue(medicationId)
//                 .orElseThrow(() ->
//                         new ResourceNotFoundException("Medication not found"));

//         Mother mother = motherRepository.findById(request.getMotherId())
//                 .orElseThrow(() ->
//                         new ResourceNotFoundException("Mother not found"));

//         medication.setMother(mother);
//         medication.setMedicationName(request.getMedicationName());
//         medication.setDosage(request.getDosage());
//         medication.setFrequency(request.getFrequency());
//         medication.setStartDate(request.getStartDate());
//         medication.setEndDate(request.getEndDate());
//         medication.setInstructions(request.getInstructions());

//         Medication updated = medicationRepository.save(medication);

//         logger.info("Medication {} updated", medicationId);

//         return medicationMapper.toResponse(updated);
//     }

    /**
 * Mother marks medication as completed
 */
public MedicationResponse markCompleted(
        Long medicationId,
        String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Mother mother = motherRepository.findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Mother profile not found"));

    Medication medication = medicationRepository
            .findByIdAndActiveTrue(medicationId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Medication not found"));

    if (!medication.getMother().getId().equals(mother.getId())) {
        throw new ResourceNotFoundException(
                "You cannot update another mother's medication.");
    }

    // Prevent completing the same medication twice
    if (medication.getStatus() == MedicationStatus.COMPLETED) {
        throw new IllegalStateException(
                "Medication has already been completed.");
    }

    medication.setStatus(MedicationStatus.COMPLETED);

    medication.setCompletedAt(java.time.LocalDateTime.now());

    Medication updated = medicationRepository.save(medication);

    logger.info(
            "Mother {} completed medication {} at {}",
            mother.getId(),
            medicationId,
            updated.getCompletedAt());

    return medicationMapper.toResponse(updated);
}

    /**
     * Soft delete medication
     */
    public void deleteMedication(Long medicationId) {

        Medication medication = medicationRepository
                .findByIdAndActiveTrue(medicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Medication not found"));

        medication.setActive(false);

        medicationRepository.save(medication);

        logger.info("Medication {} deleted", medicationId);
    }

    public List<MedicationResponse> getRecentMedications() {

    return medicationRepository
            .findTop5ByStatusAndActiveTrueOrderByCompletedAtDesc(
                    MedicationStatus.COMPLETED)
            .stream()
            .map(medicationMapper::toResponse)
            .toList();

}

/**
 * Doctor views medications of a specific mother
 */
public List<MedicationResponse> getMotherMedications(Long motherId) {

    Mother mother = motherRepository.findById(motherId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Mother not found"));

    return medicationRepository
            .findByMotherAndActiveTrueOrderByCreatedAtDesc(mother)
            .stream()
            .map(medicationMapper::toResponse)
            .toList();

}

/**
 * Get one medication by ID
 */
public MedicationResponse getMedication(
        Long medicationId,
        String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Doctor doctor = doctorRepository
            .findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor profile not found"));

    Medication medication = medicationRepository
            .findByIdAndActiveTrue(medicationId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Medication not found"));

    if (!medication.getDoctor().getId().equals(doctor.getId())) {

        throw new ResourceNotFoundException(
                "You cannot access another doctor's medication.");

    }

    return medicationMapper.toResponse(medication);

}

/**
 * Update medication
 */
public MedicationResponse updateMedication(
        Long medicationId,
        CreateMedicationRequest request,
        String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Doctor doctor = doctorRepository
            .findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor profile not found"));

    Medication medication = medicationRepository
            .findByIdAndActiveTrue(medicationId)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Medication not found"));

    if (!medication.getDoctor().getId().equals(doctor.getId())) {

        throw new ResourceNotFoundException(
                "You cannot edit another doctor's medication.");

    }

    medication.setMedicationName(request.getMedicationName());
    medication.setDosage(request.getDosage());
    medication.setFrequency(request.getFrequency());
    medication.setStartDate(request.getStartDate());
    medication.setEndDate(request.getEndDate());
    medication.setInstructions(request.getInstructions());

    Medication updated =
            medicationRepository.save(medication);

    logger.info(
            "Doctor {} updated medication {}",
            doctor.getId(),
            updated.getId());

    return medicationMapper.toResponse(updated);

}

/**
 * Doctor views all medications they prescribed
 */
public List<MedicationResponse> getDoctorMedications(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Doctor doctor = doctorRepository
            .findByUserAndActiveTrue(user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Doctor profile not found"));

    return medicationRepository
            .findByDoctorAndActiveTrueOrderByCreatedAtDesc(doctor)
            .stream()
            .map(medicationMapper::toResponse)
            .toList();
}

}