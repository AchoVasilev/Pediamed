package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Getter
public class Appointment extends BaseEntity<UUID> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @Setter
    @OneToOne
    @JoinColumn(name = "appointment_cause_id", referencedColumnName = "id")
    private AppointmentCause appointmentCause;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;
    private UUID parentId;
    private UUID patientId;
    private Integer calendarEventId;

    public Appointment(LocalDateTime startDate,
                       LocalDateTime endDate,
                       String title,
                       Integer calendarEventId,
                       AppointmentCause appointmentCause,
                       Schedule schedule,
                       UUID parentId,
                       UUID patientId) {
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.calendarEventId = Guard.Against.Zero(calendarEventId);
        this.appointmentCause = Guard.Against.Null(appointmentCause);
        this.schedule = Guard.Against.Null(schedule);
        this.parentId = Guard.Against.NullOrEmpty(parentId);
        this.patientId = Guard.Against.NullOrEmpty(patientId);
    }
}
