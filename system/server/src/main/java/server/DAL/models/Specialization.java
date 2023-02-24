package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.utils.guards.Guard;

@Entity
@Table(name = "specializations")
@NoArgsConstructor
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
