package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.doctorreview.DoctorReviewResponse;
import com.mamahealth.entity.DoctorReview;

@Component
public class DoctorReviewMapper {

    public DoctorReviewResponse toResponse(DoctorReview review) {

        DoctorReviewResponse response = new DoctorReviewResponse();

        response.setId(review.getId());

        if (review.getRecoveryRecord() != null) {
            response.setRecoveryRecordId(review.getRecoveryRecord().getId());
        }

        if (review.getDoctor() != null) {
            response.setDoctorId(review.getDoctor().getId());
        }

        response.setAssessment(review.getAssessment());
        response.setRecommendation(review.getRecommendation());
        response.setRiskLevel(review.getRiskLevel());
        response.setReviewedAt(review.getReviewedAt());

        return response;
    }
}