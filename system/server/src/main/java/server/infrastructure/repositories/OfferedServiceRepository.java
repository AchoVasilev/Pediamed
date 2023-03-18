package server.infrastructure.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.OfferedService;

@Repository
public interface OfferedServiceRepository extends BaseRepository<OfferedService, Integer> {
}
