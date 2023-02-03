package server.features.schedule.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static server.constants.ErrorMessages.REQUIRED_FIELD;

@AllArgsConstructor
@NoArgsConstructor
public class EventDataInputRequest {
    @NotBlank(message = REQUIRED_FIELD)
    private String date;
    @NotBlank(message = REQUIRED_FIELD)
    private String startHour;
    @NotBlank(message = REQUIRED_FIELD)
    private String endHour;
    private Integer intervals;
}
