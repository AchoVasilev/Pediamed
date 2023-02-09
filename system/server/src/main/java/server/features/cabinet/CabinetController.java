package server.features.cabinet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.features.cabinet.models.CabinetResponse;

import java.util.List;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    private final CabinetService cabinetService;

    public CabinetController(CabinetService cabinetService) {
        this.cabinetService = cabinetService;
    }

    @GetMapping
    public ResponseEntity<List<CabinetResponse>> getCabinets() {
        return ResponseEntity.ok(this.cabinetService.getCabinets());
    }
}
