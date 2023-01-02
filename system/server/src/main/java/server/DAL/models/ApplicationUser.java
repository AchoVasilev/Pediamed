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
    @OneToOne(mappedBy = "applicationUser",
     cascade = CascadeType.ALL)
    private Parent parent;
    @OneToOne(mappedBy = "applicationUser",
    cascade = CascadeType.ALL)
    private Doctor doctor;
    @ManyToOne
    private Role role;
    private boolean deleted = false;
}
