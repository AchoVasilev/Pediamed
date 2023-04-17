package server.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctors")
public class Doctor extends BaseEntity<UUID>{
    private String biography;
    private int yearsOfExperience;
    private int age;
    private String birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToMany(mappedBy = "doctor")
    private List<Specialization> specializations = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Cabinet> cabinets = new ArrayList<>();
}
