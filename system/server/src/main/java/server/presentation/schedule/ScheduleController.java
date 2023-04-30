package server.presentation.schedule;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.reactivestreams.Publisher;
import server.application.services.schedule.ScheduleService;
import server.application.services.schedule.models.*;
import server.application.services.sse.ServerSentEventService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static server.common.Constants.*;

@Controller("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ServerSentEventService serverSentEventService;
    public ScheduleController(ScheduleService scheduleService, ServerSentEventService serverSentEventService) {
        this.scheduleService = scheduleService;
        this.serverSentEventService = serverSentEventService;
    }

    @Post("/{id}/full")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> schedule(@PathVariable("id") UUID scheduleId, @Body AppointmentInput appointmentInput) {
        return HttpResponse.ok(this.scheduleService.scheduleAppointment(scheduleId, appointmentInput));
    }

    @Post("/{id}/registered")
    @Secured(value = {SecurityRule.IS_AUTHENTICATED, ROLE_PATIENT})
    public HttpResponse<?> schedule(@PathVariable("id") UUID scheduleId, @Body RegisteredUserAppointmentInput registeredUserAppointmentInput) {
        return HttpResponse.ok(this.scheduleService
                .scheduleAppointment(scheduleId, registeredUserAppointmentInput));
    }

    @Get("/{id}/stream")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Produces(MediaType.TEXT_EVENT_STREAM)
    public Publisher<?> getSchedule(@PathVariable("id") UUID scheduleId) {
        return serverSentEventService.send(this.scheduleService.findById(scheduleId));
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<CabinetSchedule> getScheduleById(@PathVariable("id") UUID scheduleId) {
        return HttpResponse.ok(this.scheduleService.findById(scheduleId));
    }
}
