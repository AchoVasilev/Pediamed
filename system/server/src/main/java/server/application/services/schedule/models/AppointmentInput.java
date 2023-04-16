package server.application.services.schedule.models;

import javax.validation.constraints.NotBlank;

import java.util.UUID;

import static server.common.ErrorMessages.REQUIRED_FIELD;

public record AppointmentInput(
        @NotBlank(message = REQUIRED_FIELD)
        String startDateTime,
        @NotBlank(message = REQUIRED_FIELD)
        String endDateTime,
        @NotBlank(message = REQUIRED_FIELD)
        String email,
        @NotBlank(message = REQUIRED_FIELD)
        String parentFirstName,
        @NotBlank(message = REQUIRED_FIELD)
        String parentLastName,
        @NotBlank(message = REQUIRED_FIELD)
        String phoneNumber,
        @NotBlank(message = REQUIRED_FIELD)
        String childFirstName,
        @NotBlank(message = REQUIRED_FIELD)
        String childLastName,
        UUID scheduleId,
        int eventId
) {
}
