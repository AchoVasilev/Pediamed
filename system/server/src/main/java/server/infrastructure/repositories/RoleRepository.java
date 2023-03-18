package server.infrastructure.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.Role;
import server.domain.entities.enums.RoleEnum;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends BaseRepository<Role, UUID> {
    Optional<Role> findByName(RoleEnum roleEnum);
}
