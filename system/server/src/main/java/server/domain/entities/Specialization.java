package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "specializations")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class Specialization extends BaseEntity<Integer> {
    private String name;
    private String description;
    @ManyToOne
    private Doctor doctor;

    public Specialization(String name, String description, Doctor doctor) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.description = Guard.Against.EmptyOrBlank(description);
        this.doctor = Guard.Against.Null(doctor);
    }
}
