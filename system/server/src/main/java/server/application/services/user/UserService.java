package server.application.services.user;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import server.application.services.auth.models.response.UserResponse;
import server.domain.entities.ApplicationUser;
import server.domain.repositories.UserRepository;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

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

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getEmail(),
                user.getPhoneNumber().getPhoneNumber(),
                user.getRoles().stream().map(r -> r.getName().name()).toList());
    }

    @Transactional
    @ReadOnly
    public Optional<ApplicationUser> getUserBy(String email, String firstName, String lastName, String phoneNumber) {
        return this.userRepository.findByEmailEmailAndFirstNameAndLastNameAndPhoneNumberPhoneNumber(email, firstName, lastName, phoneNumber);
    }

    private ApplicationUser getUserByEmail(String email) {
        return this.userRepository.findByEmailEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_EMAIL));
    }
}
