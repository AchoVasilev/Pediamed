package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.entities.enums.RoleEnum;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "roles")
public class Role extends BaseEntity<UUID> {
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @ManyToMany
    private List<ApplicationUser> applicationUsers;

    public Role(String name) {
        this.name = RoleEnum.valueOf(Guard.Against.EmptyOrBlank(name));
        this.applicationUsers = new ArrayList<>();
    }
}
