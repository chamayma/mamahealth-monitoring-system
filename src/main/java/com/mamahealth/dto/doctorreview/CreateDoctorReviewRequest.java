package com.mamahealth.dto.doctorreview;

import com.mamahealth.entity.RiskLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateDoctorReviewRequest {

    @NotBlank(message = "Assessment is required")
    @Size(max = 1000)
    private String assessment;

    @NotBlank(message = "Recommendation is required")
    @Size(max = 1000)
    private String recommendation;

    @NotNull(message = "Risk level is required")
    private RiskLevel riskLevel;

    public CreateDoctorReviewRequest() {
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
}