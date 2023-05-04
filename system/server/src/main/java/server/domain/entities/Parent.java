package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
}
