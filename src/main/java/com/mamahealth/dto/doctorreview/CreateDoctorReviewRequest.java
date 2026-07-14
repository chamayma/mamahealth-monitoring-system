package com.mamahealth.dto.doctorreview;

import com.mamahealth.entity.RiskLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateDoctorReviewRequest {

    @NotBlank(message = "Assessment is required")
    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Assessment must be valid text")
    private String assessment;

    @NotBlank(message = "Recommendation is required")
    @Size(max = 1000)
    @Pattern(regexp = "^[\\p{L}0-9 .,'()\\-]+$", message = "Recommendation must be valid text")
    private String recommendation;

    @NotNull(message = "Risk level is required")
    private RiskLevel riskLevel;

}