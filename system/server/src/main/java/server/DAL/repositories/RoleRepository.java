package server.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import server.DAL.models.Role;
import server.DAL.models.enums.RoleEnum;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleEnum roleEnum);
}
