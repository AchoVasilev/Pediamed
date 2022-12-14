package server.DAL.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "child")
public class Patient extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String firstName;
    private String lastName;
    private Integer age;
    boolean isDeleted = false;
    @ManyToOne
    private Parent parent;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id", referencedColumnName = "id")
    private Appointment appointment;
}
