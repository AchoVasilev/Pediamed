package server.features.cabinet;

import org.springframework.stereotype.Service;
import server.DAL.models.Cabinet;
import server.DAL.repositories.CabinetRepository;
import server.config.exceptions.models.EntityNotFoundException;
import server.features.cabinet.models.CabinetResponse;
import server.features.schedule.models.CabinetSchedule;
import server.features.schedule.models.ScheduleAppointment;
import server.features.schedule.models.ScheduleEvent;

import java.util.List;
import java.util.stream.Collectors;

import static server.constants.ErrorMessages.CABINET_NOT_FOUND;

@Service
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    public List<CabinetResponse> getCabinets() {
        var res = this.cabinetRepository
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
                                        .map(a -> new ScheduleAppointment(a.getId(), a.getStartDate(), a.getEndDate(), a.getTitle()))
                                        .collect(Collectors.toList()),
                                c.getSchedule().getCalendarEvents()
                                        .stream()
                                        .map(e -> new ScheduleEvent(e.getId(), e.getStartDate(), e.getEndDate(), e.getTitle()))
                                        .collect(Collectors.toList())
                        )
                )).collect(Collectors.toList());

        return res;
    }

    public Cabinet getCabinetByCity(String name) {
        return this.cabinetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
    }

    public void saveCabinet(Cabinet cabinet) {
        this.cabinetRepository.save(cabinet);
    }
}
