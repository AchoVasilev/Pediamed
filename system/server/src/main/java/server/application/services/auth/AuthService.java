package server.application.services.auth;

import jakarta.inject.Singleton;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.application.services.auth.models.RegistrationRequest;
import server.application.services.auth.models.UserDto;
import server.domain.entities.ApplicationUser;
import server.domain.entities.Parent;
import server.domain.entities.enums.RoleEnum;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.MobilePhone;
import server.infrastructure.config.exceptions.models.EntityAlreadyExistsException;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.RoleRepository;
import server.infrastructure.repositories.UserRepository;

import javax.transaction.Transactional;

import static server.application.constants.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static server.application.constants.ErrorMessages.ENTITY_NOT_FOUND;

@Singleton
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        var user = this.userRepository.findByEmailEmail(registrationRequest.email());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS,registrationRequest.email()));
        }

        var role = this.roleRepository.findByName(RoleEnum.ROLE_PARENT)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        var newUser = new ApplicationUser(
                new Email(registrationRequest.email()),
                this.passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.firstName(),
                registrationRequest.middleName(),
                registrationRequest.lastName(),
                new MobilePhone(registrationRequest.phoneNumber()),
                role
        );

        var patient = new Parent();
        newUser.setParent(patient);

        this.userRepository.save(newUser);
    }

    public UserDto getValidatedUser(String username, String password) {
        if (this.validateCredentials(username, password)) {
            return this.findByEmail(username);
        }

        return null;
    }

    public boolean validateCredentials(String username, String password) {
        var user = this.userRepository.findByEmailEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        return this.passwordEncoder.matches(password, user.getPassword());
    }

    public UserDto findByEmail(String email) {
        return this.userRepository.findByEmailEmail(email)
                .map(u -> new UserDto(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail().getEmail(),
                        u.getRole().getId()))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
    }
}
