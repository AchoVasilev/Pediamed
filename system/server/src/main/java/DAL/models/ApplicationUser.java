package DAL.models;

import DAL.models.interfaces.DeletableEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "application_user")
public class ApplicationUser extends AuditInfo implements DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;
    private String email;
    @OneToOne(mappedBy = "applicationUser")
    private Patient patient;
    @OneToOne(mappedBy = "applicationUser")
    private Doctor doctor;
    @OneToOne(mappedBy = "applicationUser")
    private Role role;
}
