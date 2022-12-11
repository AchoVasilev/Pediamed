package server.DAL.models;

import jakarta.persistence.OneToMany;
import org.hibernate.annotations.UuidGenerator;
import server.DAL.models.interfaces.DeletableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "role")
public class Role extends AuditInfo implements DeletableEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID Id;
    private String name;

    @OneToMany(mappedBy = "role")
    private HashSet<ApplicationUser> applicationUsers = new HashSet<>();
}
