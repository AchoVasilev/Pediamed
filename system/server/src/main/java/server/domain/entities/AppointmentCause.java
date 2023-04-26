package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_causes")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class AppointmentCause extends BaseEntity<Integer> {
    private String name;

    public AppointmentCause(String name) {
        this.name = Guard.Against.EmptyOrBlank(name);
    }
}
