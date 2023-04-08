package server.application.services.schedule.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.micronaut.core.annotation.Introspected;

import java.util.List;
import java.util.UUID;

@Introspected
@JsonInclude(JsonInclude.Include.ALWAYS)
public record CabinetSchedule (
    UUID id,
    List<ScheduleAppointment> scheduleAppointments,
    List<ScheduleEvent> scheduleEvents
){}
