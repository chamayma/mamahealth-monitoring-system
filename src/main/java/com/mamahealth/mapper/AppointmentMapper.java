package com.mamahealth.mapper;

import org.springframework.stereotype.Component;

import com.mamahealth.dto.appointment.AppointmentResponse;
import com.mamahealth.entity.Appointment;

@Component
public class AppointmentMapper {

    public AppointmentResponse toResponse(Appointment appointment) {

        AppointmentResponse response = new AppointmentResponse();

        response.setId(appointment.getId());

        if (appointment.getMother() != null) {
            response.setMotherId(appointment.getMother().getId());
        }

        if (appointment.getDoctor() != null) {
            response.setDoctorId(appointment.getDoctor().getId());
        }

        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setPurpose(appointment.getPurpose());
        response.setNotes(appointment.getNotes());
        response.setStatus(appointment.getStatus());
        response.setCreatedAt(appointment.getCreatedAt());

        return response;
    }
}