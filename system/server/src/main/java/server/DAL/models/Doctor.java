package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctors")
public class Doctor {
    @jakarta.persistence.Id
    @GeneratedValue
    @UuidGenerator
    private UUID Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private Schedule schedule;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Specialization> specializations = new ArrayList<>();
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Cabinet> cabinets = new ArrayList<>();
}
