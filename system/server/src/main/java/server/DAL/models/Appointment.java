package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends BaseEntity<UUID> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @OneToOne
    @JoinColumn(name = "appointment_cause_id", referencedColumnName = "id")
    private AppointmentCause appointmentCause;
    @ManyToOne
    private Cabinet cabinet;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;
    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Parent parent;
    @OneToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;
    @OneToOne
    @JoinColumn(name = "calendar_event_id")
    private CalendarEvent calendarEvent;
}
