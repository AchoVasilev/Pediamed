package server.infrastructure.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.EventData;

@Repository
public interface EventDataRepository extends BaseRepository<EventData, Long> {
}
