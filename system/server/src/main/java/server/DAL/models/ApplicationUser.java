package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "application_users")
public class ApplicationUser extends BaseEntity<UUID> {
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(mappedBy = "applicationUser",
     cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private Parent parent;
    @OneToOne(mappedBy = "applicationUser",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private Doctor doctor;
    @ManyToOne
    private Role role;
}
