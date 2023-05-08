package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.domain.entities.base.BaseEntity;
import server.domain.entities.enums.RoleEnum;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
@Table(name = "roles")
public class Role extends BaseEntity<UUID> {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name="application_users_roles",
            joinColumns={@JoinColumn(name="roles_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="application_user_id", referencedColumnName="id")})
    private ApplicationUser applicationUser;

    public Role(String name) {
        this.id = UUID.randomUUID();
        this.name = RoleEnum.valueOf(Guard.Against.EmptyOrBlank(name));
    }
}
