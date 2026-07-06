package com.mamahealth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mamahealth.dto.doctorreview.CreateDoctorReviewRequest;
import com.mamahealth.dto.doctorreview.DoctorReviewResponse;
import com.mamahealth.entity.Doctor;
import com.mamahealth.entity.DoctorReview;
import com.mamahealth.entity.RecoveryRecord;
import com.mamahealth.entity.User;
import com.mamahealth.exception.DuplicateResourceException;
import com.mamahealth.exception.ResourceNotFoundException;
import com.mamahealth.mapper.DoctorReviewMapper;
import com.mamahealth.repository.DoctorRepository;
import com.mamahealth.repository.DoctorReviewRepository;
import com.mamahealth.repository.RecoveryRepository;
import com.mamahealth.repository.UserRepository;

@Service
public class DoctorReviewService {

    private static final Logger logger =
            LoggerFactory.getLogger(DoctorReviewService.class);

    private final DoctorReviewRepository doctorReviewRepository;
    private final RecoveryRepository recoveryRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorReviewMapper doctorReviewMapper;

    public DoctorReviewService(
            DoctorReviewRepository doctorReviewRepository,
            RecoveryRepository recoveryRepository,
            DoctorRepository doctorRepository,
            UserRepository userRepository,
            DoctorReviewMapper doctorReviewMapper) {

        this.doctorReviewRepository = doctorReviewRepository;
        this.recoveryRepository = recoveryRepository;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.doctorReviewMapper = doctorReviewMapper;
    }

    /**
     * Create Doctor Review
     */
    public DoctorReviewResponse createReview(
            Long recoveryId,
            CreateDoctorReviewRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor profile not found"));

        RecoveryRecord recoveryRecord = recoveryRepository
                .findByIdAndActiveTrue(recoveryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recovery record not found"));

        if (doctorReviewRepository.findByRecoveryRecordAndActiveTrue(recoveryRecord).isPresent()) {
            throw new DuplicateResourceException("This recovery record has already been reviewed.");
        }

        DoctorReview review = new DoctorReview();

        review.setDoctor(doctor);
        review.setRecoveryRecord(recoveryRecord);
        review.setAssessment(request.getAssessment());
        review.setRecommendation(request.getRecommendation());
        review.setRiskLevel(request.getRiskLevel());

        DoctorReview saved = doctorReviewRepository.save(review);

        logger.info("Doctor {} reviewed recovery record {}",
                doctor.getId(), recoveryId);

        return doctorReviewMapper.toResponse(saved);
    }

    /**
     * Get Review by Recovery Record
     */
    public DoctorReviewResponse getReview(Long recoveryId) {

        RecoveryRecord recoveryRecord = recoveryRepository
                .findByIdAndActiveTrue(recoveryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Recovery record not found"));

        DoctorReview review = doctorReviewRepository
                .findByRecoveryRecordAndActiveTrue(recoveryRecord)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor review not found"));

        return doctorReviewMapper.toResponse(review);
    }

    /**
     * Update Review
     */
    public DoctorReviewResponse updateReview(
            Long reviewId,
            CreateDoctorReviewRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor profile not found"));

        DoctorReview review = doctorReviewRepository
                .findByIdAndActiveTrue(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor review not found"));

        if (!review.getDoctor().getId().equals(doctor.getId())) {
            throw new ResourceNotFoundException(
                    "You cannot update another doctor's review.");
        }

        review.setAssessment(request.getAssessment());
        review.setRecommendation(request.getRecommendation());
        review.setRiskLevel(request.getRiskLevel());

        DoctorReview updated = doctorReviewRepository.save(review);

        logger.info("Doctor {} updated review {}",
                doctor.getId(), reviewId);

        return doctorReviewMapper.toResponse(updated);
    }

    /**
     * Soft Delete Review
     */
    public void deleteReview(
            Long reviewId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Doctor doctor = doctorRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor profile not found"));

        DoctorReview review = doctorReviewRepository
                .findByIdAndActiveTrue(reviewId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor review not found"));

        if (!review.getDoctor().getId().equals(doctor.getId())) {
            throw new ResourceNotFoundException(
                    "You cannot delete another doctor's review.");
        }

        review.setActive(false);

        doctorReviewRepository.save(review);

        logger.info("Doctor {} deleted review {}",
                doctor.getId(), reviewId);
    }
}