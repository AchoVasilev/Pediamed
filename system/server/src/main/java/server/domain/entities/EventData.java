package server.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

@Entity
@Table(name = "event_data")
@Getter
@NoArgsConstructor
public class EventData extends BaseEntity<Integer>{
    private String hours;
    private Integer intervals;

    public EventData(String hours, Integer intervals) {
        this.hours = Guard.Against.EmptyOrBlank(hours);
        this.intervals = Guard.Against.Zero(intervals);
    }
}
