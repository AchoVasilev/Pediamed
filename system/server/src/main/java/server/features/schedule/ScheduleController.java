package server.features.schedule;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import server.features.schedule.models.CabinetResponse;
import server.features.schedule.models.EventDataResponse;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CabinetService cabinetService;

    public ScheduleController(ScheduleService scheduleService, CabinetService cabinetService) {
        this.scheduleService = scheduleService;
        this.cabinetService = cabinetService;
    }

    @GetMapping("/event-data")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<EventDataResponse>> getEventData() {
        return ResponseEntity.ok(this.scheduleService.getEventData());
    }

    @GetMapping("/cabinets")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CabinetResponse>> getCabinets() {
        return ResponseEntity.ok(this.cabinetService.getCabinets());
    }
}
