package server.presentation.cabinet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.cabinet.CabinetResponse;
import server.application.services.cabinet.CabinetService;

import java.util.List;

@Controller("/cabinet")
public class CabinetController {
    private final CabinetService cabinetService;

    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<List<CabinetResponse>> getCabinets() {
        return HttpResponse.ok(this.cabinetService.getCabinets());
    }

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<CabinetResponse> getCabinet(@PathVariable String name) {
        return HttpResponse.ok(this.cabinetService.getCabinetByName(name));
    }
}
