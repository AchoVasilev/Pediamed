package DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(mappedBy = "patient")
    private ApplicationUser applicationUser;
    @OneToMany(mappedBy = "patient")
    private HashSet<Appointment> appointments = new HashSet<>();
}
