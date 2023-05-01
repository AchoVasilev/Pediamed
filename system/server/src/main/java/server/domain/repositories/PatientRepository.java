package server.domain.repositories;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import server.domain.entities.Patient;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends BaseRepository<Patient, UUID> {
    @Query("SELECT *, p. ")
    @Join(value = "parent", alias = "p")
    List<Patient> findBy(String query);

    List<Patient> findAllByParentId(UUID parentId);
}
