package DAL.models;

import DAL.models.interfaces.DeletableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Entity
@Table(name = "appointment_cause")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentCause extends AuditInfo implements DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "appointmentCause")
    private HashSet<Appointment> appointment = new HashSet<>();
}
