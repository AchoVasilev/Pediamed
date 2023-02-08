package server.DAL.repositories;

import org.springframework.stereotype.Repository;
import server.DAL.models.Cabinet;

import java.util.Optional;

@Repository
public interface CabinetRepository extends BaseRepository<Cabinet, Integer> {
    Optional<Cabinet> findByCity(String name);
}
