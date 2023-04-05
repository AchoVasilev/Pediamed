package server.application.services.schedule;

import jakarta.inject.Singleton;
import server.application.services.cabinet.CabinetService;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.EventDataInputRequest;
import server.application.services.schedule.models.EventDataResponse;
import server.application.services.schedule.models.ScheduleAppointment;
import server.application.services.schedule.models.ScheduleEvent;
import server.domain.entities.CalendarEvent;
import server.infrastructure.config.exceptions.models.CalendarEventException;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.EventDataRepository;
import server.infrastructure.repositories.ScheduleRepository;
import server.infrastructure.utils.DateTimeUtility;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static server.common.ErrorMessages.EVENTS_NOT_GENERATED;
import static server.common.ErrorMessages.SCHEDULE_NOT_FOUND;
import static server.common.SuccessMessages.EVENTS_GENERATED;

@Singleton
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
                .findByDeletedFalse()
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
            var event = new CalendarEvent(slotStart, slotEnd, EVENT_TITLE, cabinet.getSchedule().getId());

            cabinet.getSchedule().addCalendarEvent(event);
        }

        if (cabinet.getSchedule().getEventsCount() == 0) {
            throw new CalendarEventException(EVENTS_NOT_GENERATED);
        }

        this.cabinetService.saveCabinet(cabinet);
        return String.format(EVENTS_GENERATED, data.startDateTime(), data.endDateTime(), data.intervals());
    }

    @Transactional
    public CabinetSchedule findById(UUID scheduleId) {
        return this.scheduleRepository.findById(scheduleId)
                .map(s -> new CabinetSchedule(s.getId(),
                        s.getAppointments()
                                .stream()
                                .map(ap -> new ScheduleAppointment(ap.getId(), ap.getStartDate(), ap.getEndDate(), ap.getTitle()))
                                .toList(),
                        s.getCalendarEvents()
                                .stream()
                                .map(e -> new ScheduleEvent(e.getId(), e.getStartDate(), e.getEndDate(), e.getTitle()))
                                .toList()))
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));
    }
}
