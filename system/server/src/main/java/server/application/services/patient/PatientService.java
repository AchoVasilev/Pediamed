package server.application.services.patient;

import jakarta.inject.Singleton;
import server.common.util.StringUtility;
import server.domain.repositories.PatientRepository;

import java.util.List;

@Singleton
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<String> findBy(String query) {
        var sanitized = StringUtility.sanitize(query);
        return List.of(sanitized);
    }
}
