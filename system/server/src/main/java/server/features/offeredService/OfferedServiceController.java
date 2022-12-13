package server.features.offeredService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import server.features.offeredService.models.OfferedServiceView;

import java.util.Set;

@RestController
public class OfferedServiceController {
    private final OfferedServiceService offeredServiceService;

    public OfferedServiceController(OfferedServiceService offeredServiceService) {
        this.offeredServiceService = offeredServiceService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Set<OfferedServiceView> getAllServices() {
        return this.offeredServiceService.getAll();
    }
}
