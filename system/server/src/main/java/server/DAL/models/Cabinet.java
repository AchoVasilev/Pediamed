package server.DAL.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.DAL.valueObjects.MobilePhone;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cabinets")
public class Cabinet extends BaseEntity<Integer> {
    private String name;
    private String city;
    private String address;
    private String postCode;
    @Embedded
    private MobilePhone mobilePhone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
    @ManyToOne
    private Doctor doctor;
}
