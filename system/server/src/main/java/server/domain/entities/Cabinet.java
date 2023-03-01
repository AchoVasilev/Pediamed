package server.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.valueObjects.MobilePhone;
import server.infrastructure.utils.guards.Guard;

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
    private MobilePhone mobilePhone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
    @ManyToOne
    private Doctor doctor;

    public Cabinet(String name, String city, String address, String postCode, MobilePhone mobilePhone, Schedule schedule, Doctor doctor) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.city = Guard.Against.EmptyOrBlank(city);
        this.address = Guard.Against.EmptyOrBlank(address);
        this.postCode = Guard.Against.EmptyOrBlank(postCode);
        this.mobilePhone = mobilePhone;
        this.schedule = Guard.Against.Null(schedule);
        this.doctor = Guard.Against.Null(doctor);
    }
}
