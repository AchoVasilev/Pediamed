package server.application.services.schedule.models;

public record ScheduleEvent (
    Integer id,
    String startDate,
    String endDate,
    String title
) {}

