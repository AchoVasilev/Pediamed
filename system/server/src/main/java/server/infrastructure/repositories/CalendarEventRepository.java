package server.infrastructure.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.CalendarEvent;

@Repository
public interface CalendarEventRepository extends BaseRepository<CalendarEvent, Integer>{
}
