package server.application.services.cabinet;

public record CabinetResponse(
        Integer id,
        String name,
        String city,
        CabinetScheduleResponse cabinetSchedule
) { }
