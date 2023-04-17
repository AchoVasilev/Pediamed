package server.domain.repositories;

import io.micronaut.data.annotation.Repository;
import server.domain.entities.AppointmentCause;

import java.util.Optional;

@Repository
public interface AppointmentCauseRepository extends BaseRepository<AppointmentCause, Integer> {
    Optional<AppointmentCause> findByIdAndDeletedFalse(Integer id);
}
