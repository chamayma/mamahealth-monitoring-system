package com.mamahealth.dto.admin;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalDoctors;
    private long totalMothers;
    private long totalRecoveryRecords;
    private long totalAppointments;
    private long totalMedications;

    public AdminDashboardResponse() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(long totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public long getTotalMothers() {
        return totalMothers;
    }

    public void setTotalMothers(long totalMothers) {
        this.totalMothers = totalMothers;
    }

    public long getTotalRecoveryRecords() {
        return totalRecoveryRecords;
    }

    public void setTotalRecoveryRecords(long totalRecoveryRecords) {
        this.totalRecoveryRecords = totalRecoveryRecords;
    }

    public long getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(long totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public long getTotalMedications() {
        return totalMedications;
    }

    public void setTotalMedications(long totalMedications) {
        this.totalMedications = totalMedications;
    }
}