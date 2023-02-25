package server.presentation.appointmentCause;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.application.services.appointmentCause.AppointmentCauseService;
import server.application.services.appointmentCause.AppointmentCauseResponse;

import java.util.List;

@RestController
@RequestMapping("/appointment-cause")
public class AppointmentCauseController {
    private final AppointmentCauseService appointmentCauseService;

    public AppointmentCauseController(AppointmentCauseService appointmentCauseService) {
        this.appointmentCauseService = appointmentCauseService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentCauseResponse>> getAll() {
        return ResponseEntity.ok(this.appointmentCauseService.getAll());
    }
}
