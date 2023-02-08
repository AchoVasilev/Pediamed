package server.features.schedule.models;

import java.time.LocalDateTime;


public record ScheduleEvent (
    Integer id,
    LocalDateTime startDate,
    LocalDateTime endDate
) {}
