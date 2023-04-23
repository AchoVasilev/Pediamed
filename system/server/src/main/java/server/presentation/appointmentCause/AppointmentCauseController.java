package server.presentation.appointmentCause;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.appointmentCause.AppointmentCauseResponse;
import server.application.services.appointmentCause.AppointmentCauseService;

import java.util.List;

@Controller("/appointment-cause")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AppointmentCauseController {
    private final AppointmentCauseService appointmentCauseService;

    public AppointmentCauseController(AppointmentCauseService appointmentCauseService) {
        this.appointmentCauseService = appointmentCauseService;
    }

    @Get
    public HttpResponse<List<AppointmentCauseResponse>> getAll() {
        return HttpResponse.ok(this.appointmentCauseService.getAll());
    }
}
