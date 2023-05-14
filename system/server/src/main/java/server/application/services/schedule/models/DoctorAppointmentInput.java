package server.application.services.schedule.models;

import javax.annotation.Nullable;
import java.util.UUID;

public record DoctorAppointmentInput(@Nullable UUID patientId, UUID eventId, String patientFirstName, String patientLastName, int appointmentCauseId) {
}
