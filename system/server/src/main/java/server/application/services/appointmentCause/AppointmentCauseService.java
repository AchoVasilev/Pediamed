package server.application.services.appointmentCause;

import jakarta.inject.Singleton;
import server.infrastructure.repositories.AppointmentCauseRepository;

import java.util.List;

@Singleton
public class AppointmentCauseService {
    private final AppointmentCauseRepository appointmentCauseRepository;

    public AppointmentCauseService(AppointmentCauseRepository appointmentCauseRepository) {
        this.appointmentCauseRepository = appointmentCauseRepository;
    }

    public List<AppointmentCauseResponse> getAll() {
        return this.appointmentCauseRepository.findByDeletedFalse()
                .stream()
                .map(a -> new AppointmentCauseResponse(a.getId(), a.getName()))
                .toList();
    }
}
