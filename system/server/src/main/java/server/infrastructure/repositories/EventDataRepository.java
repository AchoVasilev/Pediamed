package server.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.domain.entities.EventData;

@Repository
public interface EventDataRepository extends JpaRepository<EventData, Long> {
}
