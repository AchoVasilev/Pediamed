package server.application.services.schedule.models;

import java.util.UUID;

public record ScheduleEvent (
    UUID id,
    String startDate,
    String endDate,
    String title
) {}

