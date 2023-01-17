package server.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.DAL.models.EventData;

@Repository
public interface EventDataRepository extends JpaRepository<EventData, Long> {
}
