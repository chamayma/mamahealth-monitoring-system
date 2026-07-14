package com.mamahealth.dto.doctorreview;

import java.time.LocalDateTime;

import com.mamahealth.entity.RiskLevel;

import lombok.Data;

@Data
public class DoctorReviewResponse {

    private Long id;
    private Long recoveryRecordId;
    private Long doctorId;
    private String assessment;
    private String recommendation;
    private RiskLevel riskLevel;
    private LocalDateTime reviewedAt;

}