package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import server.DAL.models.enums.RoleEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID Id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @OneToMany(mappedBy = "role")
    private List<ApplicationUser> applicationUsers = new ArrayList<>();
}
