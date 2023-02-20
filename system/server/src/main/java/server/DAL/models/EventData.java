package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventData extends BaseEntity<Integer>{
    private String hours;
    private Integer intervals;
}
