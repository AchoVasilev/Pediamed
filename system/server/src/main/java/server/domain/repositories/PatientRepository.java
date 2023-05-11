package server.domain.repositories;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import server.domain.entities.Patient;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends BaseRepository<Patient, UUID> {
    @Query("SELECT * from patients p WHERE p.first_name ILIKE :query OR p.last_name ILIKE :query")
    List<Patient> findBy(String query);

    List<Patient> findAllByParentId(UUID parentId);
}
