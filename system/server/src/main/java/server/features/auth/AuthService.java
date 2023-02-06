package server.features.auth;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import server.DAL.models.ApplicationUser;
import server.DAL.models.Parent;
import server.DAL.models.enums.RoleEnum;
import server.DAL.repositories.RoleRepository;
import server.DAL.repositories.UserRepository;
import server.config.exceptions.models.EntityAlreadyExistsException;
import server.config.exceptions.models.EntityNotFoundException;
import server.features.auth.model.RegistrationRequest;
import server.features.auth.model.UserDto;

import java.util.UUID;

import static server.constants.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static server.constants.ErrorMessages.ENTITY_NOT_FOUND;

@Service
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
    public UUID register(RegistrationRequest registrationRequest) {
        var user = this.userRepository.findByEmail(registrationRequest.email());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS,registrationRequest.email()));
        }

        var newUser = new ApplicationUser();
        newUser.setEmail(registrationRequest.email());
        newUser.setPassword(this.passwordEncoder.encode(registrationRequest.password()));

        var patient = new Parent();
        newUser.setFirstName(registrationRequest.firstName());
        newUser.setMiddleName(registrationRequest.middleName());
        newUser.setLastName(registrationRequest.lastName());
        newUser.setPhoneNumber(registrationRequest.phoneNumber());

        var role = this.roleRepository.findByName(RoleEnum.ROLE_PARENT)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        newUser.setRole(role);
        newUser.setParent(patient);

        return this.userRepository.save(newUser).getId();
    }

    public UserDto findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .map(u -> new UserDto(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail()))
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
    }
}
