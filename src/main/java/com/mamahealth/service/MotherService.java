package com.mamahealth.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mamahealth.dto.mother.CreateMotherRequest;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.User;
import com.mamahealth.exception.DuplicateResourceException;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.MotherMapper;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class MotherService {

    private final MotherRepository motherRepository;
    private final UserRepository userRepository;
    private final MotherMapper motherMapper;

    public MotherService(
            MotherRepository motherRepository,
            UserRepository userRepository,
            MotherMapper motherMapper) {

        this.motherRepository = motherRepository;
        this.userRepository = userRepository;
        this.motherMapper = motherMapper;
    }

    public MotherResponse createMotherProfile(
            CreateMotherRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (motherRepository.findByUserAndActiveTrue(user).isPresent()) {
            throw new DuplicateResourceException("Mother profile already exists");
        }

        Mother mother = new Mother();

        mother.setFullName(request.getFullName());
        mother.setPhoneNumber(request.getPhoneNumber());
        mother.setDateOfBirth(request.getDateOfBirth());
        mother.setAddress(request.getAddress());
        mother.setBloodGroup(request.getBloodGroup());
        mother.setEmergencyContact(request.getEmergencyContact());
        mother.setDeliveryDate(request.getDeliveryDate());
        mother.setHospitalName(request.getHospitalName());
        mother.setActive(true);
        mother.setUser(user);

        Mother savedMother = motherRepository.save(mother);

        return motherMapper.toResponse(savedMother);
    }

    public MotherResponse getMyProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        return motherMapper.toResponse(mother);
    }

    public MotherResponse updateMyProfile(
            String email,
            CreateMotherRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        mother.setFullName(request.getFullName());
        mother.setPhoneNumber(request.getPhoneNumber());
        mother.setDateOfBirth(request.getDateOfBirth());
        mother.setAddress(request.getAddress());
        mother.setBloodGroup(request.getBloodGroup());
        mother.setEmergencyContact(request.getEmergencyContact());
        mother.setDeliveryDate(request.getDeliveryDate());
        mother.setHospitalName(request.getHospitalName());

        Mother updatedMother = motherRepository.save(mother);

        return motherMapper.toResponse(updatedMother);
    }

    public void deleteMyProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Mother mother = motherRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mother profile not found"));

        mother.setActive(false);

        motherRepository.save(mother);
    }

    public List<MotherResponse> getAllMothers() {

    return motherRepository
            .findByActiveTrueOrderByFullNameAsc()
            .stream()
            .map(motherMapper::toResponse)
            .toList();

}

public MotherResponse getMotherById(Long id) {

    Mother mother = motherRepository
            .findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Mother not found"));

    return motherMapper.toResponse(mother);

}

}