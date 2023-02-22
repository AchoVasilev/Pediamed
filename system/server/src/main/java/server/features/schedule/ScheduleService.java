package server.features.schedule;

import org.springframework.stereotype.Service;
import server.DAL.models.CalendarEvent;
import server.DAL.repositories.EventDataRepository;
import server.DAL.repositories.ScheduleRepository;
import server.config.exceptions.models.CalendarEventException;
import server.config.exceptions.models.EntityNotFoundException;
import server.features.cabinet.CabinetService;
import server.features.schedule.models.CabinetSchedule;
import server.features.schedule.models.EventDataInputRequest;
import server.features.schedule.models.EventDataResponse;
import server.features.schedule.models.ScheduleAppointment;
import server.features.schedule.models.ScheduleEvent;
import server.utils.DateTimeUtility;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static server.constants.ErrorMessages.EVENTS_GENERATED;
import static server.constants.ErrorMessages.EVENTS_NOT_GENERATED;
import static server.constants.ErrorMessages.SCHEDULE_NOT_FOUND;

@Service
public class ScheduleService {
    private final EventDataRepository eventDataRepository;
    private final CabinetService cabinetService;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(EventDataRepository eventDataRepository, CabinetService cabinetService, ScheduleRepository scheduleRepository) {
        this.eventDataRepository = eventDataRepository;
        this.cabinetService = cabinetService;
        this.scheduleRepository = scheduleRepository;
    }

    public List<EventDataResponse> getEventData() {
        return this.eventDataRepository
                .findAll()
                .stream()
                .map(ev -> new EventDataResponse(ev.getHours(), ev.getIntervals()))
                .toList();
    }

    public String generateEvents(EventDataInputRequest data) {
        var startDate = DateTimeUtility.parseDate(data.startDateTime());
        var endDate = DateTimeUtility.parseDate(data.endDateTime());

        var cabinet = this.cabinetService.getCabinetByCity(data.cabinetName());
        String EVENT_TITLE = "Свободен час";
        for (var slotStart = startDate; slotStart.isBefore(endDate); slotStart = slotStart.plusMinutes(data.intervals())) {
            var slotEnd = slotStart.plusMinutes(data.intervals());
            var event = new CalendarEvent(slotStart, slotEnd, EVENT_TITLE);

            cabinet.getSchedule().addCalendarEvent(event);
        }

        if (cabinet.getSchedule().getEventsCount() == 0) {
            throw new CalendarEventException(EVENTS_NOT_GENERATED);
        }

        this.cabinetService.saveCabinet(cabinet);
        return String.format(EVENTS_GENERATED, data.startDateTime(), data.endDateTime(), data.intervals());
    }

    public CabinetSchedule findById(UUID scheduleId) {
        var schedule = this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));

        return new CabinetSchedule(schedule.getId(),
                schedule.getAppointments()
                        .stream()
                        .map(ap -> new ScheduleAppointment(ap.getId(), ap.getStartDate(), ap.getEndDate(), ap.getTitle()))
                        .collect(Collectors.toList()),
                schedule.getCalendarEvents()
                        .stream()
                        .map(e -> new ScheduleEvent(e.getId(), e.getStartDate(), e.getEndDate(), e.getTitle()))
                        .collect(Collectors.toList()));
    }
}
