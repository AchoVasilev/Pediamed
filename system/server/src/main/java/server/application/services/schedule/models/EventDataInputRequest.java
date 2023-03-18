package server.application.services.schedule.models;


import javax.validation.constraints.NotBlank;

import static server.application.constants.ErrorMessages.REQUIRED_FIELD;

public record EventDataInputRequest(
        @NotBlank(message = REQUIRED_FIELD)
        String startDateTime,
        @NotBlank(message = REQUIRED_FIELD)
        String endDateTime,
        @NotBlank(message = REQUIRED_FIELD)
        String cabinetName,
        Integer intervals
) {
}
