package server.application.services.schedule.models;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import java.util.UUID;

import static server.common.ErrorMessages.REQUIRED_FIELD;

@Introspected
public record AppointmentInput(
        @NotBlank(message = REQUIRED_FIELD)
        String email,
        @NotBlank(message = REQUIRED_FIELD)
        String parentFirstName,
        @NotBlank(message = REQUIRED_FIELD)
        String parentLastName,
        @NotBlank(message = REQUIRED_FIELD)
        String phoneNumber,
        @NotBlank(message = REQUIRED_FIELD)
        String patientFirstName,
        @NotBlank(message = REQUIRED_FIELD)
        String patientLastName,
        UUID eventId,
        int appointmentCauseId,
        @Null
        UUID userId
) {
}
