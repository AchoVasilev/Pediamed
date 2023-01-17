package server.features.schedule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventDataResponse {
    private String hours;
    private Integer intervals;
}
