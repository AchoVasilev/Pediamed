package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.utils.guards.Guard;

@Entity
@Table(name = "appointment_causes")
@NoArgsConstructor
@Getter
public class AppointmentCause extends BaseEntity<Integer> {
    private String name;

    public AppointmentCause(String name) {
        this.name = Guard.Against.EmptyOrBlank(name);
    }
}
