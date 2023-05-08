package server.presentation.schedule;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import org.reactivestreams.Publisher;
import server.application.services.schedule.ScheduleService;
import server.application.services.schedule.models.AppointmentInput;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.RegisteredUserAppointmentInput;
import server.application.services.sse.ServerSentEventService;

import java.util.UUID;

import static server.common.Constants.ROLE_PATIENT;

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

    @Post("/{id}/user/{userId}")
    @Secured(value = {SecurityRule.IS_AUTHENTICATED, ROLE_PATIENT})
    public HttpResponse<?> schedule(@PathVariable("id") UUID scheduleId, @PathVariable("userId") UUID userId, @Body RegisteredUserAppointmentInput registeredUserAppointmentInput) {
        return HttpResponse.ok(this.scheduleService
                .scheduleAppointment(scheduleId, userId, registeredUserAppointmentInput));
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
