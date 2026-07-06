package com.mamahealth.dto.doctorreview;

import java.time.LocalDateTime;

import com.mamahealth.entity.RiskLevel;

public class DoctorReviewResponse {

    private Long id;
    private Long recoveryRecordId;
    private Long doctorId;
    private String assessment;
    private String recommendation;
    private RiskLevel riskLevel;
    private LocalDateTime reviewedAt;

    public DoctorReviewResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecoveryRecordId() {
        return recoveryRecordId;
    }

    public void setRecoveryRecordId(Long recoveryRecordId) {
        this.recoveryRecordId = recoveryRecordId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }
}