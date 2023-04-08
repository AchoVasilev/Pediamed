package server.application.services.appointmentCause;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import server.infrastructure.repositories.AppointmentCauseRepository;

import javax.transaction.Transactional;
import java.util.List;

@Singleton
public class AppointmentCauseService {
    private final AppointmentCauseRepository appointmentCauseRepository;

    public AppointmentCauseService(AppointmentCauseRepository appointmentCauseRepository) {
        this.appointmentCauseRepository = appointmentCauseRepository;
    }

    @Transactional
    @ReadOnly
    public List<AppointmentCauseResponse> getAll() {
        return this.appointmentCauseRepository.findByDeletedFalse()
                .stream()
                .map(a -> new AppointmentCauseResponse(a.getId(), a.getName()))
                .toList();
    }
}
