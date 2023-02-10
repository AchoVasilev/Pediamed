package server.features.schedule.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleAppointment(
        UUID id,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String title
) {
}
