package server.application.services.cabinet;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Introspected;
import server.application.services.schedule.models.CabinetSchedule;

import java.util.List;

@Introspected
@JsonInclude(JsonInclude.Include.ALWAYS)
public record CabinetResponse(
        Integer id,
        String name,
        String city,
        CabinetSchedule cabinetSchedule,
        List<Integer> workDays
) { }
