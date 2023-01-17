package server.DAL.repositories;

import org.springframework.stereotype.Repository;
import server.DAL.models.Cabinet;

@Repository
public interface CabinetRepository extends BaseRepository<Cabinet, Integer> {
}
