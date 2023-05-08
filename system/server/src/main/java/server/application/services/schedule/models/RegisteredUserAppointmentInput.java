package server.application.services.schedule.models;

import javax.annotation.Nullable;
import java.util.UUID;

public record RegisteredUserAppointmentInput(
        UUID patientId,
        UUID eventId,
        int appointmentCauseId,
        @Nullable
        String patientFirstName,
        @Nullable
        String patientLastName){
}
