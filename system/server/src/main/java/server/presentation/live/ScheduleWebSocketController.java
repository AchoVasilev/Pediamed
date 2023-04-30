package server.presentation.live;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import server.application.services.schedule.ScheduleService;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.ScheduleAppointment;
import server.common.util.CalendarEventsUtility;
import server.domain.entities.Schedule;
import server.events.AppointmentScheduled;
import server.infrastructure.utils.DateTimeUtility;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@ServerWebSocket("/ws/schedule/{topic}/{scheduleId}")
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class ScheduleWebSocketController implements ApplicationEventListener<AppointmentScheduled> {
    private final WebSocketBroadcaster webSocketBroadcaster;
    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper;
    private final Map<String, WebSocketSession> sessions;

    public ScheduleWebSocketController(WebSocketBroadcaster webSocketBroadcaster, ScheduleService scheduleService, ObjectMapper objectMapper) {
        this.webSocketBroadcaster = webSocketBroadcaster;
        this.scheduleService = scheduleService;
        this.objectMapper = objectMapper;
        this.sessions = new ConcurrentHashMap<>();
    }

    @OnOpen
    public Publisher<String> onOpen(String topic, String scheduleId, WebSocketSession webSocketSession) {
        log.info(topic + ' ' + scheduleId);
        this.sessions.put(webSocketSession.getId(), webSocketSession);
        String scheduleStr = getScheduleJson(scheduleId);

        return this.publishMessage(scheduleStr, webSocketSession);
    }

    private String getScheduleJson(String scheduleId) {
        var schedule = this.scheduleService.findById(UUID.fromString(scheduleId));
        var scheduleStr = "";
        try {
            scheduleStr = this.objectMapper.writeValueAsString(schedule);
        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
        }
        return scheduleStr;
    }

    @OnMessage
    public Publisher<String> onMessage(String topic, String scheduleId, String message, WebSocketSession webSocketSession) {
        this.sessions.put(webSocketSession.getId(), webSocketSession);
        var msg = this.getScheduleJson(scheduleId);

        return this.publishMessage(msg, webSocketSession);
    }

    @OnClose
    public void onClose(WebSocketSession webSocketSession) {
        this.sessions.remove(webSocketSession.getId(), webSocketSession);
    }

    private Predicate<WebSocketSession> isSchedule(String topic) {
        return s -> topic.equals("schedule")
                || "schedule".equals(s.getUriVariables().get("topic", String.class, null))
                || topic.equalsIgnoreCase(s.getUriVariables().get("topic", String.class, null));
    }

    @Override
    public void onApplicationEvent(AppointmentScheduled event) {
        var scheduleStr = "";
        try {
            var schedule = (Schedule) event.getSource();
            var result = new CabinetSchedule(schedule.getId(),
                    schedule.getAppointments()
                            .stream()
                            .filter(ap -> !ap.getDeleted())
                            .map(ap -> new ScheduleAppointment(ap.getId(), DateTimeUtility.parseToString(ap.getStartDate()),
                                    DateTimeUtility.parseToString(ap.getEndDate()), ap.getTitle()))
                            .toList(),
                    CalendarEventsUtility.mapEvents(schedule.getCalendarEvents()));

            scheduleStr = this.objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }

        for (String sessionId : this.sessions.keySet()) {
            publishMessage(scheduleStr, sessions.get(sessionId));
        }
    }

    public Publisher<String> publishMessage(String message, WebSocketSession session) {
        return this.webSocketBroadcaster.broadcast(message);
    }
}
