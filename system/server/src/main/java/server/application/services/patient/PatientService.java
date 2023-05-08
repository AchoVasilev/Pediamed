package server.application.services.patient;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import server.application.services.user.UserService;
import server.common.util.StringUtility;
import server.domain.repositories.PatientRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Singleton
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserService userService;

    public PatientService(PatientRepository patientRepository, UserService userService) {
        this.patientRepository = patientRepository;
        this.userService = userService;
    }

    public List<String> findBy(String query) {
        var sanitized = StringUtility.sanitize(query);
        return List.of(sanitized);
    }

    @Transactional
    @ReadOnly
    public List<PatientView> findAllByParentId(UUID userId) {
        return this.userService.getUser(userId)
                .getParent()
                .getPatients()
                .stream()
                .map(p -> new PatientView(p.getId(), p.getFirstName(), p.getLastName()))
                .toList();
    }
}
