package com.mamahealth.service;

import org.springframework.stereotype.Service;

import com.mamahealth.dto.mother.CreateMotherRequest;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.entity.Mother;
import com.mamahealth.entity.User;
import com.mamahealth.repository.MotherRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class MotherService {

    private final MotherRepository motherRepository;
    private final UserRepository userRepository;

    public MotherService(
            MotherRepository motherRepository,
            UserRepository userRepository) {

        this.motherRepository = motherRepository;
        this.userRepository = userRepository;
    }

    public MotherResponse createMotherProfile(
            CreateMotherRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (motherRepository.findByUser(user).isPresent()) {
            throw new RuntimeException(
                    "Mother profile already exists");
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

        mother.setUser(user);

        Mother savedMother = motherRepository.save(mother);

        MotherResponse response = new MotherResponse();

        response.setId(savedMother.getId());
        response.setFullName(savedMother.getFullName());
        response.setPhoneNumber(savedMother.getPhoneNumber());
        response.setDateOfBirth(savedMother.getDateOfBirth());
        response.setAddress(savedMother.getAddress());
        response.setBloodGroup(savedMother.getBloodGroup());
        response.setEmergencyContact(savedMother.getEmergencyContact());
        response.setDeliveryDate(savedMother.getDeliveryDate());
        response.setHospitalName(savedMother.getHospitalName());
        response.setEmail(user.getEmail());

        return response;
    }

    public MotherResponse getMyProfile(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Mother mother = motherRepository.findByUser(user)
            .orElseThrow(() ->
                    new RuntimeException("Mother profile not found"));

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
    response.setEmail(user.getEmail());

    return response;
    }

    public MotherResponse updateMyProfile(
        String email,
        CreateMotherRequest request) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Mother mother = motherRepository.findByUser(user)
            .orElseThrow(() ->
                    new RuntimeException("Mother profile not found"));

    mother.setFullName(request.getFullName());
    mother.setPhoneNumber(request.getPhoneNumber());
    mother.setDateOfBirth(request.getDateOfBirth());
    mother.setAddress(request.getAddress());
    mother.setBloodGroup(request.getBloodGroup());
    mother.setEmergencyContact(request.getEmergencyContact());
    mother.setDeliveryDate(request.getDeliveryDate());
    mother.setHospitalName(request.getHospitalName());

    Mother updatedMother = motherRepository.save(mother);

    MotherResponse response = new MotherResponse();

    response.setId(updatedMother.getId());
    response.setFullName(updatedMother.getFullName());
    response.setPhoneNumber(updatedMother.getPhoneNumber());
    response.setDateOfBirth(updatedMother.getDateOfBirth());
    response.setAddress(updatedMother.getAddress());
    response.setBloodGroup(updatedMother.getBloodGroup());
    response.setEmergencyContact(updatedMother.getEmergencyContact());
    response.setDeliveryDate(updatedMother.getDeliveryDate());
    response.setHospitalName(updatedMother.getHospitalName());
    response.setEmail(user.getEmail());

    return response;
    }

    public void deleteMyProfile(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Mother mother = motherRepository.findByUser(user)
            .orElseThrow(() ->
                    new RuntimeException("Mother profile not found"));

    motherRepository.delete(mother);
    }
}