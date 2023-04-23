package server.presentation.cabinet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.cabinet.CabinetResponse;
import server.application.services.cabinet.CabinetService;

@Controller("/cabinet")
public class CabinetController {
    private final CabinetService cabinetService;

    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @Get("/{id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<CabinetResponse> getCabinet(@PathVariable("id") Integer id) {
        return HttpResponse.ok(this.cabinetService.getCabinetById(id));
    }
}
