package server.DAL.repositories;

import org.springframework.stereotype.Repository;
import server.DAL.models.ApplicationUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByEmail(String email);
}
