package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.entities.enums.RoleEnum;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "roles")
public class Role extends BaseEntity<UUID> {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToOne
    @Setter
    private ApplicationUser applicationUser;

    public Role(String name) {
        this.name = RoleEnum.valueOf(Guard.Against.EmptyOrBlank(name));
    }
}
