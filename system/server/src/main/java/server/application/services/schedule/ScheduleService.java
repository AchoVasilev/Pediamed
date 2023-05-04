package server.application.services.schedule;

import io.micronaut.security.utils.SecurityService;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import server.application.services.appointmentCause.AppointmentCauseService;
import server.application.services.schedule.models.AppointmentInput;
import server.application.services.schedule.models.CabinetSchedule;
import server.application.services.schedule.models.RegisteredUserAppointmentInput;
import server.application.services.schedule.models.ScheduleAppointment;
import server.application.services.user.UserService;
import server.common.util.CalendarEventsUtility;
import server.domain.entities.Appointment;
import server.domain.entities.Patient;
import server.domain.entities.Schedule;
import server.domain.entities.enums.RoleEnum;
import server.domain.repositories.ScheduleRepository;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.utils.DateTimeUtility;

import javax.transaction.Transactional;
import java.util.UUID;

import static server.common.ErrorMessages.SCHEDULE_NOT_FOUND;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserService userService;
    private final AppointmentCauseService appointmentCauseService;
    private final SecurityService securityService;

    @Transactional
    @ReadOnly
    public CabinetSchedule findById(UUID scheduleId) {
        return this.scheduleRepository.findById(scheduleId)
                .map(s -> new CabinetSchedule(s.getId(),
                        s.getAppointments()
                                .stream()
                                .filter(ap -> !ap.getDeleted())
                                .map(ap -> new ScheduleAppointment(ap.getId(), DateTimeUtility.parseToString(ap.getStartDate()),
                                        DateTimeUtility.parseToString(ap.getEndDate()), this.convertAppointmentTitle(ap.getTitle())))
                                .toList(),
                        CalendarEventsUtility.mapEvents(s.getCalendarEvents())))
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));
    }

    @Transactional
    public ScheduleAppointment scheduleAppointment(UUID scheduleId, AppointmentInput appointmentInput) {
        var user = this.userService.createUnregisteredParent(
                appointmentInput.email(),
                appointmentInput.parentFirstName(),
                appointmentInput.parentLastName(),
                appointmentInput.phoneNumber(),
                appointmentInput.patientFirstName(),
                appointmentInput.patientLastName());

        var patient = user.getPatientBy(appointmentInput.patientFirstName(), appointmentInput.patientLastName());

        log.info("Scheduling appointment for unregistered user. [userId={}, patientId={}]", user.getId(), patient.getId());
        return this.scheduleAppointment(scheduleId, appointmentInput.eventId(), appointmentInput.appointmentCauseId(), patient, user.getParent().getId());
    }

    @Transactional
    public ScheduleAppointment scheduleAppointment(UUID scheduleId, UUID userId, RegisteredUserAppointmentInput registeredUserAppointmentInput) {
        var user = this.userService.getUser(userId);
        var patient = user.getPatientBy(registeredUserAppointmentInput.patientId(), registeredUserAppointmentInput.patientFirstName(), registeredUserAppointmentInput.patientLastName());

        log.info("Scheduling appointment for registered user. [userId={}, patientId={}]", user.getId(), patient.getId());
        return this.scheduleAppointment(scheduleId, registeredUserAppointmentInput.eventId(), registeredUserAppointmentInput.appointmentCauseId(), patient, user.getParent().getId());
    }

    private ScheduleAppointment scheduleAppointment(UUID scheduleId, UUID eventId, Integer appointmentCauseId, Patient patient, UUID parentId) {
        var title = this.createAppointmentTitle(patient.getFirstName(), patient.getLastName());
        var schedule = this.getScheduleById(scheduleId);

        var appointmentCause = this.appointmentCauseService.findById(appointmentCauseId);

        var event = schedule.getEventBy(eventId);
        var appointment = new Appointment(event.getStartDate(),
                event.getEndDate(),
                title, event.getId(),
                appointmentCause,
                schedule.getId(),
                parentId,
                patient.getId());

        event.markDeleted(true);
        schedule.getAppointments().add(appointment);
        this.scheduleRepository.update(schedule);

        log.info("Created appointment. [appointmentId={}, scheduleId={}]", appointment.getId(), schedule.getId());
        return new ScheduleAppointment(appointment.getId(), DateTimeUtility.parseToString(appointment.getStartDate()),
                DateTimeUtility.parseToString(appointment.getEndDate()), appointment.getTitle());
    }

    private String createAppointmentTitle(String childFirstName, String childLastName) {
        return String.format("Запазен час за %s %s", childFirstName, childLastName);
    }

    private String convertAppointmentTitle(String title) {
        var auth = this.securityService.getAuthentication();
        if (auth.isPresent() && auth.get().getRoles().contains("[" + RoleEnum.ROLE_DOCTOR + "]"))
            return title;

        return "Запазен час";
    }

    private Schedule getScheduleById(UUID scheduleId) {
        return this.scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND));
    }
}
