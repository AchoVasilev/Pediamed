package server.features.cabinet.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.features.schedule.models.CabinetSchedule;

@AllArgsConstructor
@Getter
public class CabinetResponse {
    private Integer id;
    private String name;
    private String city;
    private CabinetSchedule cabinetSchedule;
}
