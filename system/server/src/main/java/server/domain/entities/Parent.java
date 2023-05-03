package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static server.common.ErrorMessages.PATIENT_ID_NOT_FOUND;
import static server.common.ErrorMessages.PATIENT_NOT_FOUND;

@Entity
@Getter
@Table(name = "parents")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Parent extends BaseEntity<UUID> {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "parent")
    private final List<Patient> patients;

    public Parent(ApplicationUser applicationUser) {
        this.id = UUID.randomUUID();
        this.applicationUser = applicationUser;
        this.patients = new ArrayList<>();
    }

    public Patient getPatientBy(String firstName, String lastName) {
        return this.getPatientByNames(firstName, lastName)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PATIENT_NOT_FOUND, firstName, lastName)));
    }

    public Optional<Patient> getPatientByNames(String firstName, String lastName) {
        return this.patients.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName) && !c.getDeleted())
                .findFirst();
    }

    public Patient getPatientBy(UUID patientId) {
        return this.patients.stream()
                .filter(p -> p.getId() == patientId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(PATIENT_ID_NOT_FOUND));
    }
}
