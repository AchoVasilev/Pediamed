package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.DAL.valueObjects.Email;
import server.DAL.valueObjects.MobilePhone;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "application_users")
public class ApplicationUser extends BaseEntity<UUID> {
    @Embedded
    private Email email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    @Embedded
    private MobilePhone phoneNumber;
    @Setter
    @OneToOne(mappedBy = "applicationUser",
     cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private Parent parent;
    @Setter
    @OneToOne(mappedBy = "applicationUser",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private Doctor doctor;
    @ManyToOne
    private Role role;

    public ApplicationUser(Email email, String password, String firstName, String middleName, String lastName, MobilePhone phoneNumber, Role role) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
