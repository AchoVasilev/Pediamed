package DAL.models;

import DAL.models.interfaces.DeletableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cabinet")
public class Cabinet extends AuditInfo implements DeletableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String city;
    private String address;
    private String postCode;
    private String phoneNumber;
    @OneToMany(mappedBy = "cabinet")
    private HashSet<Appointment> appointments = new HashSet<>();
}
