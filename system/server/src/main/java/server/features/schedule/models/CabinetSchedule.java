package server.features.schedule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CabinetSchedule {
    private UUID id;
    private List<ScheduleAppointment> scheduleAppointments;
    private List<ScheduleEvent> scheduleEvents;
}
