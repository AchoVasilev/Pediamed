package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.entities.base.BaseEntity;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Appointment extends BaseEntity<UUID> {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String title;
    @Setter
    @OneToOne
    @JoinColumn(name = "appointment_cause_id", referencedColumnName = "id")
    private AppointmentCause appointmentCause;
    @Column(name = "schedule_id")
    private UUID scheduleId;
    @ManyToOne
    private Patient patient;
    private UUID calendarEventId;

    public Appointment(ZonedDateTime startDate,
                       ZonedDateTime endDate,
                       String title,
                       UUID calendarEventId,
                       AppointmentCause appointmentCause,
                       UUID scheduleId,
                       Patient patient) {
        this.id = UUID.randomUUID();
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.calendarEventId = Guard.Against.NullOrEmpty(calendarEventId);
        this.appointmentCause = Guard.Against.Null(appointmentCause);
        this.scheduleId = Guard.Against.NullOrEmpty(scheduleId);
        this.patient = Guard.Against.Null(patient);
    }
}
