package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@NoArgsConstructor
@Getter
@Setter
@Table(name = "parents")
public class Parent extends BaseEntity<UUID>{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();
}
