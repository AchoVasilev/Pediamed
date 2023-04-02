package server.presentation.cabinet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
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
    public HttpResponse<List<CabinetResponse>> getCabinets() {
        return HttpResponse.ok(this.cabinetService.getCabinets());
    }
}
