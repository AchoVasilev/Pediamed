package server.application.services.appointmentCause;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import server.domain.entities.AppointmentCause;
import server.domain.repositories.AppointmentCauseRepository;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.transaction.Transactional;
import java.util.List;

import static server.common.ErrorMessages.INVALID_APPOINTMENT_CAUSE;

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

    public AppointmentCause findById(Integer id) {
        return this.appointmentCauseRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_APPOINTMENT_CAUSE));
    }
}
