package server.features.appointmentCause;

import org.springframework.stereotype.Service;
import server.DAL.repositories.AppointmentCauseRepository;
import server.features.appointmentCause.models.AppointmentCauseResponse;

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
