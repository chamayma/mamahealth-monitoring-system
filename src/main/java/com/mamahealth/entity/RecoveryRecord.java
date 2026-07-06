package com.mamahealth.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

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

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public RecoveryRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mother getMother() {
        return mother;
    }

    public void setMother(Mother mother) {
        this.mother = mother;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Integer getPainLevel() {
        return painLevel;
    }

    public void setPainLevel(Integer painLevel) {
        this.painLevel = painLevel;
    }

    public Double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(Double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public WoundCondition getWoundCondition() {
        return woundCondition;
    }

    public void setWoundCondition(WoundCondition woundCondition) {
        this.woundCondition = woundCondition;
    }

    public BleedingLevel getBleedingLevel() {
        return bleedingLevel;
    }

    public void setBleedingLevel(BleedingLevel bleedingLevel) {
        this.bleedingLevel = bleedingLevel;
    }

    public Mobility getMobility() {
        return mobility;
    }

    public void setMobility(Mobility mobility) {
        this.mobility = mobility;
    }

    public Boolean getMedicationTaken() {
        return medicationTaken;
    }

    public void setMedicationTaken(Boolean medicationTaken) {
        this.medicationTaken = medicationTaken;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}