package server.features.schedule;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.features.schedule.models.EventDataInputRequest;
import server.features.schedule.models.EventDataResponse;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/event-data")
    public ResponseEntity<List<EventDataResponse>> getEventData() {
        return ResponseEntity.ok(this.scheduleService.getEventData());
    }

    @PostMapping("/event-data")
    public ResponseEntity<?> createEvents(@RequestBody @Valid EventDataInputRequest data) {
        this.scheduleService.generateEvents(data);
        return ResponseEntity.ok("");
    }
}
