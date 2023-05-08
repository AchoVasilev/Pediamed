package server.common.util;

import server.application.services.schedule.models.ScheduleEvent;
import server.domain.entities.CalendarEvent;
import server.infrastructure.utils.DateTimeUtility;

import java.util.List;

public class CalendarEventsUtility {
    public static List<ScheduleEvent> mapEvents(List<CalendarEvent> events) {
        return events.stream()
                .filter(ce -> !ce.getDeleted())
                .map(e -> new ScheduleEvent(e.getId(), DateTimeUtility.parseToString(e.getStartDate()),
                        DateTimeUtility.parseToString(e.getEndDate()), e.getTitle()))
                .toList();
    }
}
