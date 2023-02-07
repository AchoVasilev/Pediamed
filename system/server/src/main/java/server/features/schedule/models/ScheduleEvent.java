package server.features.schedule.models;

import java.time.LocalDateTime;
import java.util.UUID;


public record ScheduleEvent (
    UUID id,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
