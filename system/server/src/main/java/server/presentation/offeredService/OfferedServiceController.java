package server.presentation.offeredService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.offeredService.OfferedServiceService;
import server.application.services.offeredService.OfferedServiceView;

import java.util.Set;

@Controller("/offered-service")
public class OfferedServiceController {
    private final OfferedServiceService offeredServiceService;

    public OfferedServiceController(OfferedServiceService offeredServiceService) {
        this.offeredServiceService = offeredServiceService;
    }

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<Set<OfferedServiceView>> getAllServices() {
        return HttpResponse.ok(this.offeredServiceService.getAll());
    }
}
