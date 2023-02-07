package server.features.cabinet.models;

import server.features.schedule.models.CabinetSchedule;

public record CabinetResponse(
        Integer id,
        String name,
        String city,
        CabinetSchedule cabinetSchedule
) { }
