package server.application.services.schedule;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import server.application.services.appointmentCause.AppointmentCauseService;
import server.application.services.cabinet.CabinetService;
import server.application.services.schedule.models.*;
import server.application.services.user.UserService;
import server.domain.entities.Appointment;
import server.domain.entities.CalendarEvent;
import server.domain.entities.Schedule;
import server.domain.repositories.EventDataRepository;
import server.domain.repositories.ScheduleRepository;
import server.infrastructure.config.exceptions.models.CalendarEventException;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.utils.DateTimeUtility;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

import static server.common.ErrorMessages.EVENTS_NOT_GENERATED;
import static server.common.ErrorMessages.SCHEDULE_NOT_FOUND;
import static server.common.SuccessMessages.EVENTS_GENERATED;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final EventDataRepository eventDataRepository;
    private final CabinetService cabinetService;
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final AppointmentCauseService appointmentCauseService;

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
    public String generateEvents(EventDataInputRequest data) {
        var startDate = DateTimeUtility.parseDate(data.startDateTime());
        var endDate = DateTimeUtility.parseDate(data.endDateTime());

        var cabinet = this.cabinetService.getCabinetByCity(data.cabinetName());

        DateTimeUtility.validateWorkDays(cabinet.getWorkDays(), DayOfWeek.from(startDate).name(), DayOfWeek.from(endDate).name());
        String EVENT_TITLE = "Свободен час";
        for (var slotStart = startDate; slotStart.isBefore(endDate); slotStart = slotStart.plusMinutes(data.intervals())) {
            var slotEnd = slotStart.plusMinutes(data.intervals());
            var event = new CalendarEvent(slotStart, slotEnd, EVENT_TITLE, cabinet.getSchedule().getId());

            cabinet.getSchedule().addCalendarEvent(event);
        }

        if (cabinet.getSchedule().getEventsCount() == 0) {
            throw new CalendarEventException(EVENTS_NOT_GENERATED);
        }

        this.cabinetService.updateCabinet(cabinet);
        log.info("Successfully generated events. [startDate={}, endDate={}, intervals={}, cabinetId={}, scheduleId={}]",
                data.startDateTime(), data.endDateTime(), data.intervals(), cabinet.getId(), cabinet.getSchedule().getId());
        return String.format(EVENTS_GENERATED, data.startDateTime(), data.endDateTime(), data.intervals());
    }

    @Transactional
    @ReadOnly
    public CabinetSchedule findById(UUID scheduleId) {
        return this.scheduleRepository.findById(scheduleId)
                .map(s -> new CabinetSchedule(s.getId(),
                        s.getAppointments()
                                .stream()
                                .filter(ap -> !ap.getDeleted())
                                .map(ap -> new ScheduleAppointment(ap.getId(), DateTimeUtility.parseToString(ap.getStartDate()),
                                       DateTimeUtility.parseToString(ap.getEndDate()), ap.getTitle()))
                                .toList(),
                        s.getCalendarEvents()
                                .stream()
                                .filter(ce -> !ce.getDeleted())
                                .map(e -> new ScheduleEvent(e.getId(),DateTimeUtility.parseToString(e.getStartDate()),
                                        DateTimeUtility.parseToString(e.getEndDate()), e.getTitle()))
                                .toList()))
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));
    }

    @Transactional
    public void scheduleAppointment(UUID scheduleId, AppointmentInput appointmentInput) {

        var user = this.userService.createUnregisteredParent(
                appointmentInput.email(),
                appointmentInput.parentFirstName(),
                appointmentInput.parentLastName(),
                appointmentInput.phoneNumber(),
                appointmentInput.patientFirstName(),
                appointmentInput.patientLastName());

        var patient = user.getParent().getPatientBy(appointmentInput.patientFirstName(), appointmentInput.patientLastName());
        var title = this.createAppointmentTitle(appointmentInput.patientFirstName(), appointmentInput.patientLastName());
        var schedule = this.getScheduleById(scheduleId);

        var event = schedule.getEventBy(appointmentInput.eventId());
        event.markDeleted(true);
        var appointmentCause = this.appointmentCauseService.findById(appointmentInput.appointmentCauseId());

        var appointment = new Appointment(event.getStartDate(), event.getEndDate(), title, event.getId(), appointmentCause, schedule.getId(), user.getParent().getId(), patient.getId());

        schedule.getAppointments().add(appointment);
        this.scheduleRepository.update(schedule);
    }

    private String createAppointmentTitle(String childFirstName, String childLastName) {
        return String.format("Запазен час за %s %s", childFirstName, childLastName);
    }

    private Schedule getScheduleById(UUID scheduleId) {
        return this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));
    }
}
