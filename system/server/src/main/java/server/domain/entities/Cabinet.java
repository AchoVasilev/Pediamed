package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "cabinets")
public class Cabinet extends BaseEntity<Integer> {
    private String name;
    private String city;
    private String address;
    private String postCode;
    @Embedded
    private PhoneNumber phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cabinet")
    private Schedule schedule;
    @ManyToOne
    private Doctor doctor;

    public Cabinet(String name, String city, String address, String postCode, PhoneNumber phoneNumber, Schedule schedule, Doctor doctor) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.city = Guard.Against.EmptyOrBlank(city);
        this.address = Guard.Against.EmptyOrBlank(address);
        this.postCode = Guard.Against.EmptyOrBlank(postCode);
        this.phoneNumber = phoneNumber;
        this.schedule = Guard.Against.Null(schedule);
        this.doctor = Guard.Against.Null(doctor);
    }
}
