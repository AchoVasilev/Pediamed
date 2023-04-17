package server.domain.entities;

import lombok.Getter;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static server.common.ErrorMessages.PATIENT_NOT_FOUND;

@Entity
@Getter
@Table(name = "parents")
public class Parent extends BaseEntity<UUID> {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "parent")
    private final List<Patient> patients;

    public Parent() {
        this.patients = new ArrayList<>();
    }

    public Patient getPatientBy(String firstName, String lastName) {
        return this.patients.stream()
                .filter(c -> c.getFirstName().equals(firstName) && c.getLastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(String.format(PATIENT_NOT_FOUND, firstName, lastName)));
    }
}
