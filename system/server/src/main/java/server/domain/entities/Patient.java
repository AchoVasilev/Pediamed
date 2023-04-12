package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "patients")
public class Patient extends BaseEntity<UUID> {
    private String firstName;
    private String lastName;
    private Integer age;
    private String birthDate;
    @ManyToOne
    private Parent parent;

    public Patient (String firstName, String lastName, Integer age, String birthDate, Parent parent) {
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
        this.age = this.validateAge(age);
        this.birthDate = Guard.Against.Null(birthDate);
        this.parent = Guard.Against.Null(parent);
    }

    private Integer validateAge(Integer age) {
        Guard.Against.Null(age);
        if (age < 0 || age > 130) {
            throw new IllegalArgumentException("Invalid age");
        }

        return age;
    }
}
