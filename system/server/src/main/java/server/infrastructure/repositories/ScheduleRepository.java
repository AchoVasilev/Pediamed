package server.infrastructure.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.Schedule;

import java.util.UUID;

@Repository
public interface ScheduleRepository extends BaseRepository<Schedule, UUID> {
}
