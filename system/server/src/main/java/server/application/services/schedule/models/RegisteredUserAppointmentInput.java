package server.application.services.schedule.models;

import java.util.UUID;

public record RegisteredUserAppointmentInput(
        UUID patientId,
        UUID eventId,
        int appointmentCauseId){
}
