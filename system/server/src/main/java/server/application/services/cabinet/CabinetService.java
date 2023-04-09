package server.application.services.cabinet;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.ScheduleAppointment;
import server.application.services.schedule.models.ScheduleEvent;
import server.domain.entities.Cabinet;
import server.domain.entities.enums.CabinetWorkDays;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.CabinetRepository;
import server.infrastructure.utils.DateTimeUtility;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static server.common.ErrorMessages.CABINET_NOT_FOUND;

@Singleton
public class CabinetService {
    private final CabinetRepository cabinetRepository;

    public CabinetService(CabinetRepository cabinetRepository) {
        this.cabinetRepository = cabinetRepository;
    }

    @Transactional
    @ReadOnly
    public Cabinet getCabinetByCity(String name) {
        return this.cabinetRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
    }

    @Transactional
    public void saveCabinet(Cabinet cabinet) {
        this.cabinetRepository.save(cabinet);
    }

    @Transactional
    @ReadOnly
    public CabinetResponse getCabinetByName(String name) {
        return this.cabinetRepository.findByName(name)
                .map(c -> new CabinetResponse(
                        c.getId(),
                        c.getName(),
                        c.getCity(),
                        new CabinetSchedule(c.getSchedule().getId(),
                                c.getSchedule().getAppointments()
                                        .stream()
                                        .map(ap -> new ScheduleAppointment(ap.getId(), ap.getStartDate(), ap.getEndDate(), ap.getTitle()))
                                        .toList(),
                                c.getSchedule().getCalendarEvents()
                                        .stream()
                                        .map(e -> new ScheduleEvent(e.getId(), DateTimeUtility.parseToString(e.getStartDate()),
                                                DateTimeUtility.parseToString(e.getEndDate()), e.getTitle()))
                                        .toList()),
                        this.calculateWorkDays(c.getWorkDays())
                )).orElseThrow(() -> new EntityNotFoundException(CABINET_NOT_FOUND));
    }

    private List<Integer> calculateWorkDays(List<String> workDays) {
        var result = new ArrayList<Integer>(workDays.size());
        for (var day : workDays) {
            for (var workDay : CabinetWorkDays.values()) {
                if (workDay.name().equalsIgnoreCase(day)) {
                    result.add(workDay.ordinal());
                    break;
                }
            }
        }

        return result;
    }
}
