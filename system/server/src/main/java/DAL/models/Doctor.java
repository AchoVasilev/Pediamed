package DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID Id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    @OneToOne(mappedBy = "doctor")
    private ApplicationUser applicationUser;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Specialization> specializations = new ArrayList<>();
}
