package com.mamahealth.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
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

}