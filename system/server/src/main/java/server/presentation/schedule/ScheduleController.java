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

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final String DOCTOR_ROLE = "ROLE_DOCTOR";
    private final String ADMIN_ROLE = "ROLE_ADMINISTRATOR";

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Get("/event-data")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<List<EventDataResponse>> getEventData() {
        return HttpResponse.ok(this.scheduleService.getEventData());
    }

    @Post("/event-data")
    // @Secured(value = {SecurityRule.IS_AUTHENTICATED, DOCTOR_ROLE, ADMIN_ROLE})
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> createEvents(@Body @Valid EventDataInputRequest data) {
        return HttpResponse.ok(new EventResponse(this.scheduleService.generateEvents(data)));
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<CabinetSchedule> getCabinetSchedule(@PathVariable("id") UUID scheduleId) {
        return HttpResponse.ok(this.scheduleService.findById(scheduleId));
    }
}
