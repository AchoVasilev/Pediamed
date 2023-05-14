package server.domain.repositories;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import server.application.services.patient.PatientView;
import server.domain.entities.Patient;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends BaseRepository<Patient, UUID> {
    @Query(value = "SELECT CAST(p.id AS VARCHAR) id, p.first_name AS firstName, p.last_name AS lastName FROM patients p " +
            "WHERE p.first_name ILIKE CONCAT('%', :query, '%') " +
            "OR p.last_name ILIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<PatientView> findBy(String query);
}
