package server.application.services.user;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import server.application.services.auth.models.response.DoctorResponse;
import server.application.services.auth.models.response.ParentResponse;
import server.application.services.auth.models.response.PatientResponse;
import server.application.services.auth.models.response.UserResponse;
import server.domain.entities.ApplicationUser;
import server.domain.entities.enums.RoleEnum;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.UserRepository;

import javax.transaction.Transactional;

import static server.common.ErrorMessages.INVALID_EMAIL;

@Singleton
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @ReadOnly
    public UserResponse getUser(String name) {
        var user = this.getUserByEmail(name);
        log.info("Exporting user [userId={}, username={}]", user.getId(), user.getEmail().getEmail());

        var userRoleNames = user.getRoles().stream().map(r -> r.getName().name()).toList();
        if (userRoleNames.contains(RoleEnum.ROLE_PARENT.name())) {
            return new ParentResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getMiddleName(),
                    user.getLastName(),
                    user.getEmail().getEmail(),
                    user.getPhoneNumber().getPhoneNumber(),
                    userRoleNames,
                    user.getParent().getPatients().stream().map(p -> new PatientResponse(p.getFirstName(), p.getLastName(), p.getAge(), p.getBirthDate())).toList());
        }

        return new DoctorResponse(
                user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getEmail().getEmail(),
                user.getPhoneNumber().getPhoneNumber(),
                userRoleNames);
    }

    private ApplicationUser getUserByEmail(String email) {
        return this.userRepository.findByEmailEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_EMAIL));
    }
}
