package server.application.services.calendar;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import server.application.services.cabinet.CabinetService;
import server.application.services.schedule.models.EventDataInputRequest;
import server.application.services.schedule.models.EventDataResponse;
import server.application.services.schedule.models.ScheduleEvent;
import server.common.util.CalendarEventsUtility;
import server.domain.entities.CalendarEvent;
import server.domain.repositories.EventDataRepository;
import server.infrastructure.config.exceptions.models.CalendarEventException;
import server.infrastructure.utils.DateTimeUtility;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static server.common.ErrorMessages.EVENTS_NOT_GENERATED;

@Singleton
@Slf4j
public class CalendarService {
    private final EventDataRepository eventDataRepository;
    private final CabinetService cabinetService;
    public CalendarService(EventDataRepository eventDataRepository, CabinetService cabinetService) {
        this.eventDataRepository = eventDataRepository;
        this.cabinetService = cabinetService;
    }

    @Transactional
    @ReadOnly
    public List<EventDataResponse> getEventData() {
        return this.eventDataRepository
                .findByDeletedFalse()
                .stream()
                .map(ev -> new EventDataResponse(ev.getHours(), ev.getIntervals()))
                .toList();
    }


    @Transactional
    public List<ScheduleEvent> generateEvents(EventDataInputRequest data) {
        var cabinet = this.cabinetService.getCabinetByCity(data.cabinetName());
        var zoneId = ZoneId.of(cabinet.getTimeZone());
        var startDate = DateTimeUtility.parseDateTime(data.startDateTime(), zoneId);
        var endDate = DateTimeUtility.parseDateTime(data.endDateTime(), zoneId);

        DateTimeUtility.validateWorkDays(cabinet.getWorkDays(), DayOfWeek.from(startDate).name(), DayOfWeek.from(endDate).name());
        String EVENT_TITLE = "Свободен час";
        var events = new ArrayList<CalendarEvent>();
        for (var slotStart = startDate; slotStart.isBefore(endDate); slotStart = slotStart.plusMinutes(data.intervals())) {
            var slotEnd = slotStart.plusMinutes(data.intervals());
            var event = new CalendarEvent(slotStart, slotEnd, EVENT_TITLE, cabinet.getSchedule().getId());

            if (cabinet.getSchedule().addCalendarEvent(event))
                events.add(event);
            else
                log.info("Skipping duplicate. [eventStartDate={}], [eventEndDate={}]", event.getStartDate(), event.getEndDate());
        }

        if (events.isEmpty()) {
            throw new CalendarEventException(EVENTS_NOT_GENERATED);
        }

        this.cabinetService.updateCabinet(cabinet);
        log.info("Successfully generated events. [eventsCount={}] [startDate={}, endDate={}, intervals={}, cabinetId={}, scheduleId={}]",
                events.size(), data.startDateTime(), data.endDateTime(), data.intervals(), cabinet.getId(), cabinet.getSchedule().getId());
        return CalendarEventsUtility.mapEvents(events);
    }
}
