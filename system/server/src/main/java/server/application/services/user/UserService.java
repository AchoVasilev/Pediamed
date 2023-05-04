package server.application.services.user;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import server.application.services.auth.models.response.UserResponse;
import server.application.services.role.RoleService;
import server.domain.entities.ApplicationUser;
import server.domain.entities.enums.RoleEnum;
import server.domain.repositories.UserRepository;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static server.common.ErrorMessages.INVALID_EMAIL;
import static server.common.ErrorMessages.USER_NOT_FOUND;

@Singleton
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    @ReadOnly
    public UserResponse getUser(String name) {
        var user = this.getUserByEmail(name);
        log.info("Exporting user [userId={}, username={}]", user.getId(), user.getEmail().getEmail());

        return this.mapUser(user);
    }

    @Transactional
    @ReadOnly
    public ApplicationUser getUser(UUID userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    @Transactional
    public ApplicationUser createUnregisteredParent(String email, String firstName, String lastName, String phoneNumber, String patientFirstName, String patientLastName) {
        var user = this.getUserBy(email, firstName, lastName, phoneNumber);
        if (user.isPresent()) {
            return user.get();
        }

        var parentRole = this.roleService.findByName(RoleEnum.ROLE_PARENT);
        var newUser = new ApplicationUser(new Email(email), firstName, lastName, new PhoneNumber(phoneNumber));
        newUser.addUserToRole(parentRole);
        newUser.addParent();
        newUser.addPatientToParent(patientFirstName, patientLastName);

        log.info("Created unregistered user. [userId={}, parentId={}]", newUser.getId(), newUser.getParent().getId());
        return this.save(newUser);
    }

    @Transactional
    public ApplicationUser save(ApplicationUser applicationUser) {
        return this.userRepository.save(applicationUser);
    }

    @Transactional
    public ApplicationUser update(ApplicationUser applicationUser) {
        return this.userRepository.update(applicationUser);
    }

    @Transactional
    @ReadOnly
    public ApplicationUser getUserByEmail(String email) {
        return this.getByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_EMAIL));
    }

    @Transactional
    @ReadOnly
    public Optional<ApplicationUser> getByEmail(String email) {
        return this.userRepository.findByEmailEmail(email);
    }

    private UserResponse mapUser(ApplicationUser user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getEmail(),
                user.getPhoneNumber().getPhoneNumber(),
                user.getRoles().stream().map(r -> r.getName().name()).toList());
    }

    private Optional<ApplicationUser> getUserBy(String email, String firstName, String lastName, String phoneNumber) {
        return this.userRepository.findByEmailEmailAndFirstNameAndLastNameAndPhoneNumberPhoneNumber(email, firstName, lastName, phoneNumber);
    }
}
