package server.features.auth;

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

    public UUID register(RegistrationRequest registrationRequest) {
        var user = this.userRepository.findByEmail(registrationRequest.getEmail());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS,registrationRequest.getEmail()));
        }

        var newUser = new ApplicationUser();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(this.passwordEncoder.encode(registrationRequest.getPassword()));

        var patient = new Parent();
        patient.setFirstName(registrationRequest.getFirstName());
        patient.setMiddleName(registrationRequest.getMiddleName());
        patient.setLastName(registrationRequest.getLastName());
        patient.setPhoneNumber(registrationRequest.getPhoneNumber());

        var role = this.roleRepository.findByName(RoleEnum.ROLE_PARENT)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));
        newUser.setRole(role);
        newUser.setParent(patient);

        return this.userRepository.save(newUser).getId();
    }
}
