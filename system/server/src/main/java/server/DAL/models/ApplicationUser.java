package server.DAL.models;

import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "application_user")
public class ApplicationUser extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID Id;
    private String email;
    private String password;
    @OneToOne(mappedBy = "applicationUser")
    private Patient patient;
    @OneToOne(mappedBy = "applicationUser")
    private Doctor doctor;
    @ManyToOne
    private Role role;
    private boolean deleted = false;
}
