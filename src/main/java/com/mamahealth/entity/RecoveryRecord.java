package com.mamahealth.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "recovery_records")
public class RecoveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id", nullable = false)
    private Mother mother;

    @Column(nullable = false)
    private LocalDate recordDate;

    @Column(nullable = false)
    private Integer painLevel;

    @Column(nullable = false)
    private Double bodyTemperature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WoundCondition woundCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BleedingLevel bleedingLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mobility mobility;

    @Column(nullable = false)
    private Boolean medicationTaken;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean active = true;

    private RecoveryIndicator recoveryIndicator;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}