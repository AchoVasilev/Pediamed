package server.infrastructure.repositories;

import org.springframework.stereotype.Repository;
import server.domain.entities.OfferedService;

@Repository
public interface OfferedServiceRepository extends BaseRepository<OfferedService, Integer> {
}
