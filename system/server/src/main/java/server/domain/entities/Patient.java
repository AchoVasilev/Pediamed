package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Table(name = "patients")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Patient extends BaseEntity<UUID> {
    private String firstName;
    private String lastName;
    private Integer age;
    private String birthDate;
    @ManyToOne
    private Parent parent;

    public Patient (String firstName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = Guard.Against.EmptyOrBlank(firstName);
        this.lastName = Guard.Against.EmptyOrBlank(lastName);
    }

    public void setAge(int age) {
        this.age = this.validateAge(age);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = Guard.Against.EmptyOrBlank(birthDate);
    }

    private Integer validateAge(Integer age) {
        Guard.Against.Null(age);
        if (age < 0 || age > 130) {
            throw new IllegalArgumentException("Invalid age");
        }

        return age;
    }
}
