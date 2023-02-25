package server.application.services.appointmentCause;

import org.springframework.stereotype.Service;
import server.infrastructure.repositories.AppointmentCauseRepository;

import java.util.List;

@Service
public class AppointmentCauseService {
    private final AppointmentCauseRepository appointmentCauseRepository;

    public AppointmentCauseService(AppointmentCauseRepository appointmentCauseRepository) {
        this.appointmentCauseRepository = appointmentCauseRepository;
    }

    public List<AppointmentCauseResponse> getAll() {
        return this.appointmentCauseRepository.findAll()
                .stream()
                .map(a -> new AppointmentCauseResponse(a.getId(), a.getName()))
                .toList();
    }
}
