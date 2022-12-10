package DAL.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "application_user")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;
    private String userName;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @ManyToOne
    private Role role;
}
