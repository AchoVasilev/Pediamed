package server.presentation.calendarEvent;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.calendar.CalendarService;
import server.application.services.schedule.models.EventDataInputRequest;
import server.application.services.schedule.models.EventDataResponse;
import server.application.services.schedule.models.ScheduleEvent;

import javax.validation.Valid;
import java.util.List;

import static server.common.Constants.ROLE_ADMINISTRATOR;
import static server.common.Constants.ROLE_DOCTOR;

@Controller("/calendar")
public class CalendarEventController {
    private final CalendarService calendarService;

    public CalendarEventController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Get("/event-data")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<List<EventDataResponse>> getEventData() {
        return HttpResponse.ok(this.calendarService.getEventData());
    }

    @Post
    @Secured(value = {SecurityRule.IS_AUTHENTICATED, ROLE_DOCTOR, ROLE_ADMINISTRATOR})
    public HttpResponse<List<ScheduleEvent>> createEvents(@Body @Valid EventDataInputRequest data) {
        return HttpResponse.ok(this.calendarService.generateEvents(data));
    }
}
