package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private HashSet<Appointment> appointments = new HashSet<>();
}
