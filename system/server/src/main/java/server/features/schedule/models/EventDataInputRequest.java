package server.features.schedule.models;

import jakarta.validation.constraints.NotBlank;

import static server.constants.ErrorMessages.REQUIRED_FIELD;

public record EventDataInputRequest(
        @NotBlank(message = REQUIRED_FIELD)
        String startDateTime,
        @NotBlank(message = REQUIRED_FIELD)
        String endDateTime,
        Integer intervals
) {
}
