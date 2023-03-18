package server.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.MobilePhone;
import server.infrastructure.utils.guards.Guard;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "applicationUser")
    private List<Role> roles;

    public ApplicationUser(Email email, String password, String firstName, String middleName, String lastName, MobilePhone phoneNumber) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = Guard.Against.EmptyOrBlank(password);
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.middleName = Guard.Against.EmptyOrBlank(middleName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
    }

    public ApplicationUser(Email email, String password, String firstName, String middleName, String lastName, MobilePhone phoneNumber, List<Role> roles) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = Guard.Against.EmptyOrBlank(password);
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.middleName = Guard.Against.EmptyOrBlank(middleName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }
}
