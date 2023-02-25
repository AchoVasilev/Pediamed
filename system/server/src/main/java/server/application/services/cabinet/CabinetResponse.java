package server.application.services.cabinet;

import server.application.services.schedule.models.CabinetSchedule;

public record CabinetResponse(
        Integer id,
        String name,
        String city,
        CabinetSchedule cabinetSchedule
) { }
