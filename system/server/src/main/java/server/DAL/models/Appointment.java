package server.DAL.models;

import org.hibernate.annotations.UuidGenerator;
import server.DAL.models.interfaces.DeletableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "appointment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment extends AuditInfo implements DeletableEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @ManyToOne
    private AppointmentCause appointmentCause;
    @ManyToOne
    private Cabinet cabinet;
    @ManyToOne
    private Patient patient;
}
