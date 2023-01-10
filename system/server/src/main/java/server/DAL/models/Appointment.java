package server.DAL.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne
    private AppointmentCause appointmentCause;
    @ManyToOne
    private Cabinet cabinet;
    @ManyToOne
    private Schedule schedule;
    @OneToOne
    private Parent parent;
    @OneToOne
    private Patient patient;
    private boolean deleted = false;
}
