package server.application.services.schedule.models;

public record EventDataResponse(
        String hours,
        Integer intervals
) { }
