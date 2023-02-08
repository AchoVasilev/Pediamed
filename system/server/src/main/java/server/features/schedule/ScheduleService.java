package server.features.schedule;

import org.springframework.stereotype.Service;
import server.DAL.models.CalendarEvent;
import server.DAL.repositories.EventDataRepository;
import server.config.exceptions.models.CalendarEventException;
import server.features.cabinet.CabinetService;
import server.features.schedule.models.EventDataInputRequest;
import server.features.schedule.models.EventDataResponse;
import server.utils.DateTimeUtility;

import java.util.List;

import static server.constants.ErrorMessages.EVENTS_NOT_GENERATED;

@Service
public class ScheduleService {
    private  final EventDataRepository eventDataRepository;
    private final CabinetService cabinetService;

    public ScheduleService(EventDataRepository eventDataRepository, CabinetService cabinetService) {
        this.eventDataRepository = eventDataRepository;
        this.cabinetService = cabinetService;
    }

    public List<EventDataResponse> getEventData() {
        return this.eventDataRepository
                .findAll()
                .stream()
                .map(ev -> new EventDataResponse(ev.getHours(), ev.getIntervals()))
                .toList();
    }

    public void generateEvents(EventDataInputRequest data) {
        var startDate = DateTimeUtility.parseDate(data.startDateTime());
        var endDate = DateTimeUtility.parseDate(data.endDateTime());
        DateTimeUtility.validateDate(startDate, endDate);

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
    }
}
