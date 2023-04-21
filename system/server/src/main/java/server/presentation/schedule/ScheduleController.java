package server.presentation.schedule;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.schedule.ScheduleService;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.EventDataInputRequest;
import server.application.services.schedule.models.EventDataResponse;
import server.application.services.schedule.models.EventResponse;
import server.application.services.schedule.models.AppointmentInput;
import server.application.services.schedule.models.RegisteredUserAppointmentInput;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static server.common.Constants.ROLE_ADMINISTRATOR;
import static server.common.Constants.ROLE_DOCTOR;
import static server.common.Constants.ROLE_PATIENT;

@Controller("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
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

    @Get("/event-data")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<List<EventDataResponse>> getEventData() {
        return HttpResponse.ok(this.scheduleService.getEventData());
    }

    @Post("/event-data")
    @Secured(value = {SecurityRule.IS_AUTHENTICATED, ROLE_DOCTOR, ROLE_ADMINISTRATOR})
    public HttpResponse<EventResponse> createEvents(@Body @Valid EventDataInputRequest data) {
        return HttpResponse.ok(new EventResponse(this.scheduleService.generateEvents(data)));
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<CabinetSchedule> getCabinetSchedule(@PathVariable("id") UUID scheduleId) {
        return HttpResponse.ok(this.scheduleService.findById(scheduleId));
    }
}
