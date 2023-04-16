package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "application_users")
@NoArgsConstructor
public class ApplicationUser extends BaseEntity<UUID> {
    @Embedded
    private Email email;
    private String password;
    private String firstName;
    private String lastName;
    @Embedded
    private PhoneNumber phoneNumber;
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

    @OneToMany(mappedBy = "applicationUser")
    private List<Role> roles;

    private String salt;

    public ApplicationUser(Email email, String password, String firstName, String lastName, PhoneNumber phoneNumber) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = Guard.Against.EmptyOrBlank(password);
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
        this.salt = UUID.randomUUID().toString();
    }

    public void invalidateSalt() {
        this.salt = UUID.randomUUID().toString();
    }
}
