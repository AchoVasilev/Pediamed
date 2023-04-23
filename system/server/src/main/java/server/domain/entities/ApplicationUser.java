package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.*;
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
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    private Parent parent;
    @Setter
    @OneToOne(mappedBy = "applicationUser",
            fetch = FetchType.LAZY)
    private Doctor doctor;

    @OneToMany(mappedBy = "applicationUser")
    private List<Role> roles;

    private String salt;

    public ApplicationUser(Email email, String password, String firstName, String lastName, PhoneNumber phoneNumber) {
        this.email = email;
        this.password = Guard.Against.EmptyOrBlank(password);
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
        this.salt = UUID.randomUUID().toString();
    }

    public ApplicationUser(Email email, String firstName, String lastName, PhoneNumber phoneNumber) {
        this.email = email;
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
    }

    public void invalidateSalt() {
        this.salt = UUID.randomUUID().toString();
    }
}
