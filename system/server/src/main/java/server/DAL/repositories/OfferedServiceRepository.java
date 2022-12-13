package server.DAL.repositories;

import org.springframework.stereotype.Repository;
import server.DAL.models.OfferedService;

@Repository
public interface OfferedServiceRepository extends BaseRepository<OfferedService, Integer> {
}
