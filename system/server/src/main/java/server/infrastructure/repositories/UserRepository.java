package server.infrastructure.repositories;

import org.springframework.stereotype.Repository;
import server.domain.entities.ApplicationUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<ApplicationUser, UUID> {
    //@Query("SELECT u.id, u.first_name, u.last_name, u.email FROM application_users u")
    Optional<ApplicationUser> findByEmail(String email);
}
