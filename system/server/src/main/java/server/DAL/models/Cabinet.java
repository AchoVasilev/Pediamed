package server.DAL.models;

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
public class Cabinet extends AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String city;
    private String address;
    private String postCode;
    private String phoneNumber;
    @OneToMany(mappedBy = "cabinet", cascade = CascadeType.ALL)
    private HashSet<Appointment> appointments = new HashSet<>();
    private boolean deleted = false;
}
