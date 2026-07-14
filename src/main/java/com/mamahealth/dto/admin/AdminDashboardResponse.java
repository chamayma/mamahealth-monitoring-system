package com.mamahealth.dto.admin;

import lombok.Data;

@Data
public class AdminDashboardResponse {

    private long totalUsers;
    private long totalDoctors;
    private long totalMothers;
    private long totalRecoveries;
    private long totalMedications;
    private long totalAppointments;
    private long totalNotifications;

    
}