package server.domain.repositories;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import server.domain.entities.Schedule;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends BaseRepository<Schedule, UUID> {
    @Query("")
    Schedule findScheduleById(UUID id);
}
