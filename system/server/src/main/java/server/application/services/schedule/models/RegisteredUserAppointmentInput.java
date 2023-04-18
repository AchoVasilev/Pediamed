package server.application.services.schedule.models;

import java.util.UUID;

public record RegisteredUserAppointmentInput(
        UUID userId,
        UUID patientId,
        int eventId,
        int appointmentCauseId){
}
