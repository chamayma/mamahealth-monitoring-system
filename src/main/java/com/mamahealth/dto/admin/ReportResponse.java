package com.mamahealth.dto.admin;

import lombok.Data;

@Data
public class ReportResponse {

    private long totalDoctors;

    private long totalMothers;

    private long totalAppointments;

    private long completedAppointments;

    private long missedAppointments;

    private long activeMedications;

    private long completedMedications;

    private long recoveryReports;

    private long notifications;

}
