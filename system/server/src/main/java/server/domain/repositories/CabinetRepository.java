package server.domain.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.Cabinet;

import java.util.Optional;

@Repository
public interface CabinetRepository extends BaseRepository<Cabinet, Integer> {
    Optional<Cabinet> findByName(String name);
}
