package com.mamahealth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_reviews")
public class DoctorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Recovery record being reviewed
     */
    @OneToOne
    @JoinColumn(name = "recovery_record_id", nullable = false, unique = true)
    private RecoveryRecord recoveryRecord;

    /**
     * Doctor who wrote the review
     */
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(nullable = false, length = 1000)
    private String assessment;

    @Column(nullable = false, length = 1000)
    private String recommendation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private LocalDateTime reviewedAt;

    @Column(nullable = false)
    private Boolean active = true;

    @PrePersist
    public void prePersist() {
        reviewedAt = LocalDateTime.now();
    }

    public DoctorReview() {
    }

    public Long getId() {
        return id;
    }

    public RecoveryRecord getRecoveryRecord() {
        return recoveryRecord;
    }

    public void setRecoveryRecord(RecoveryRecord recoveryRecord) {
        this.recoveryRecord = recoveryRecord;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}