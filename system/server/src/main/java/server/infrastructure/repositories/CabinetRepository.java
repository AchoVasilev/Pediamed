package server.infrastructure.repositories;

import org.springframework.stereotype.Repository;
import server.domain.entities.Cabinet;

import java.util.Optional;

@Repository
public interface CabinetRepository extends BaseRepository<Cabinet, Integer> {
    Optional<Cabinet> findByName(String name);
}
