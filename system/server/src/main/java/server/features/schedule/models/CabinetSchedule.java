package server.features.schedule.models;

import java.util.List;
import java.util.UUID;


public record CabinetSchedule (
    UUID id,
    List<ScheduleAppointment> scheduleAppointments,
    List<ScheduleEvent> scheduleEvents
){}
