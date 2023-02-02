package server.features.cabinet;

import org.springframework.stereotype.Service;
import server.DAL.repositories.CabinetRepository;
import server.features.cabinet.models.CabinetResponse;
import server.features.schedule.models.CabinetSchedule;
import server.features.schedule.models.ScheduleAppointment;
import server.features.schedule.models.ScheduleEvent;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    public List<CabinetResponse> getCabinets() {
        return this.cabinetRepository
                .findAll()
                .stream()
                .map(c -> new CabinetResponse(
                        c.getId(),
                        c.getName(),
                        c.getCity(),
                        new CabinetSchedule(
                                c.getSchedule().getId(),
                                c.getSchedule().getAppointments()
                                        .stream()
                                        .map(a -> new ScheduleAppointment(a.getId(), a.getStartDate(), a.getEndDate()))
                                        .collect(Collectors.toList()),
                                c.getSchedule().getCalendarEvents()
                                        .stream()
                                        .map(e -> new ScheduleEvent(e.getId(), e.getStartDate(), e.getEndDate()))
                                        .collect(Collectors.toList())
                        )
                )).collect(Collectors.toList());
    }
}
