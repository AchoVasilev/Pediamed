package server.DAL.repositories;

import server.DAL.models.ApplicationUser;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByEmail(String email);
}
