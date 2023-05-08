package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.entities.base.BaseEntity;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "event_data")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class EventData extends BaseEntity<Integer> {
    private String hours;
    private Integer intervals;

    public EventData(String hours, Integer intervals) {
        this.hours = Guard.Against.EmptyOrBlank(hours);
        this.intervals = Guard.Against.Zero(intervals);
    }
}
