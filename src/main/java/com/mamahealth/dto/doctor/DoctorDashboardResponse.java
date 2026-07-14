package com.mamahealth.dto.doctor;

import java.util.List;

import com.mamahealth.dto.appointment.AppointmentResponse;
import com.mamahealth.dto.medication.MedicationResponse;
import com.mamahealth.dto.mother.MotherResponse;
import com.mamahealth.dto.notification.NotificationResponse;

import lombok.Data;

@Data
public class DoctorDashboardResponse {

    private long totalMothers;

    private long recoveryReports;

    private long activeMedications;

    private long upcomingAppointments;

    private List<MotherResponse> recentMothers;

    private List<AppointmentResponse> todayAppointments;

    private List<MedicationResponse> recentMedications;

    private List<NotificationResponse> recentNotifications;


}