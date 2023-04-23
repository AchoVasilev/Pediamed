package server.application.services.schedule.models;


import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

import static server.common.ErrorMessages.REQUIRED_FIELD;

@Introspected
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
