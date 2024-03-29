package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.entities.base.BaseEntity;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static server.common.ErrorMessages.PATIENT_NOT_FOUND;

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
    @OneToOne(mappedBy = "applicationUser",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
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
        this(email, firstName, lastName, phoneNumber);
        this.password = Guard.Against.EmptyOrBlank(password);
        this.salt = UUID.randomUUID().toString();
    }

    public ApplicationUser(Email email, String firstName, String lastName, PhoneNumber phoneNumber) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.phoneNumber = phoneNumber;
        this.roles = new ArrayList<>();
    }

    public void invalidateSalt() {
        this.salt = UUID.randomUUID().toString();
    }

    public void addParent() {
        this.parent = new Parent(this);
    }

    public void addPatientToParent(String patientFirstName, String patientLastName) {
        if (this.getPatientByNames(patientFirstName, patientLastName).isEmpty()) {
            this.getParent()
                    .getPatients()
                    .add(new Patient(patientFirstName, patientLastName, this.getParent()));
        }
    }

    public void addUserToRole(Role role) {
        this.roles.add(role);
        role.setApplicationUser(this);
    }

    public Patient getPatientBy(String firstName, String lastName) {
        return this.getPatientByNames(firstName, lastName)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PATIENT_NOT_FOUND, firstName, lastName)));
    }

    public Optional<Patient> getPatientByNames(String firstName, String lastName) {
        return this.parent.getPatients().stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName) && !c.getDeleted())
                .findFirst();
    }

    public Patient checkPatientNames(Patient patient, String firstName, String lastName) {
        if (firstName != null && !patient.getFirstName().equals(firstName)) {
            patient.changeFirstName(firstName);
        }

        if (lastName != null && !patient.getLastName().equals(lastName)) {
            patient.changeLastName(lastName);
        }

        return patient;
    }

    public Patient addPatient(String firstName, String lastName) {
        var patient = new Patient(firstName, lastName, this.parent);
        this.parent.getPatients().add(patient);
        return patient;
    }

    public Optional<Patient> getPatientBy(UUID patientId) {
        return this.parent.getPatients().stream()
                .filter(p -> p.getId().equals(patientId))
                .findFirst();
    }
}
