package server.DAL.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
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
