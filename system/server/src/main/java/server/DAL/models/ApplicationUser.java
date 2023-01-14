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
@Table(name = "application_users")
public class ApplicationUser extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID Id;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(mappedBy = "applicationUser",
     cascade = CascadeType.ALL)
    private Parent parent;
    @OneToOne(mappedBy = "applicationUser",
    cascade = CascadeType.ALL)
    private Doctor doctor;
    @ManyToOne
    private Role role;
}
