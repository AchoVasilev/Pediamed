package server.domain.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.AppointmentCause;

@Repository
public interface AppointmentCauseRepository extends BaseRepository<AppointmentCause, Integer> {
}
