package server.application.services.auth;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import server.application.services.auth.models.dto.UserDto;
import server.application.services.auth.models.dto.UserValidation;
import server.application.services.auth.models.request.RegistrationRequest;
import server.application.services.auth.models.response.LoginResponse;
import server.domain.entities.ApplicationUser;
import server.domain.entities.Parent;
import server.domain.entities.enums.RoleEnum;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.config.exceptions.models.EntityAlreadyExistsException;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;
import server.infrastructure.repositories.RoleRepository;
import server.infrastructure.repositories.UserRepository;
import server.infrastructure.utils.TokenService;

import javax.transaction.Transactional;

import static server.common.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static server.common.ErrorMessages.ENTITY_NOT_FOUND;
import static server.common.ErrorMessages.INVALID_CREDENTIALS;
import static server.common.ErrorMessages.INVALID_EMAIL;

@Singleton
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        var user = this.userRepository.findByEmailEmail(registrationRequest.email());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS, registrationRequest.email()));
        }

        var role = this.roleRepository.findByName(RoleEnum.ROLE_PARENT)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND));

        var newUser = new ApplicationUser(
                new Email(registrationRequest.email()),
                this.passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.firstName(),
                registrationRequest.middleName(),
                registrationRequest.lastName(),
                new PhoneNumber(registrationRequest.phoneNumber())
        );

        role.setApplicationUser(newUser);
        newUser.getRoles().add(role);

        var parent = new Parent();
        newUser.setParent(parent);

        this.userRepository.save(newUser);

        log.info(String.format("User with id=%s successfully registered", newUser.getId()));
    }

    @Transactional
    public Publisher<MutableHttpResponse<?>> authenticate(Publisher<AuthenticationResponse> auth) {
        return Flux.from(auth)
                .map(authenticationResponse -> {
                    if (authenticationResponse.isAuthenticated() && authenticationResponse.getAuthentication().isPresent()) {
                        var authentication = authenticationResponse.getAuthentication().get();
                        var loginResponse = this.getLoginResponse(authentication);
                        log.info("User logged in. [username={}, id={}]", authentication.getName(), loginResponse.id());

                        return HttpResponse.ok(loginResponse);
                    } else {
                        return HttpResponse.badRequest(INVALID_CREDENTIALS);
                    }
                }).switchIfEmpty(Mono.defer(() -> Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED))));
    }

    @Transactional
    @ReadOnly
    public UserValidation getValidatedUser(String username, String password) {
        log.info("User trying to authenticate [username={}]", username);
        var user = this.validateCredentials(username, password);
        if (user.isValid()) {
            return user;
        }

        return null;
    }

    private UserValidation validateCredentials(String username, String password) {
        var user = this.userRepository.findByEmailEmail(username)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS));

        return new UserValidation(
                new UserDto(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getEmail(),
                        user.getPhoneNumber().getPhoneNumber(),
                        user.getRoles().stream().map(r -> r.getName().name()).toList()),
                this.passwordEncoder.matches(password, user.getPassword()),
                user.getSalt()
        );
    }

    @Transactional
    @ReadOnly
    public UserDto findByEmail(String email) {
        return this.userRepository.findByEmailEmail(email)
                .map(u -> new UserDto(
                        u.getId(),
                        u.getFirstName(),
                        u.getMiddleName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getPhoneNumber().getPhoneNumber(),
                        u.getRoles().stream().map(r -> r.getName().name()).toList()))
                .orElseThrow(() -> new EntityNotFoundException(INVALID_CREDENTIALS));
    }

    @Transactional
    public void logOut(Authentication authentication) {
        var user = this.getUserByEmail(authentication.getName());
        user.invalidateSalt();
        this.userRepository.update(user);
        log.info("User logged out [user={}, userId={}]", user.getEmail(), user.getId());
    }

    private LoginResponse getLoginResponse(Authentication authentication) {
        var user = this.findByEmail(authentication.getName());
        return new LoginResponse(
                user.id(),
                this.tokenService.generateToken(authentication)
        );
    }

    private ApplicationUser getUserByEmail(String email) {
        return this.userRepository.findByEmailEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(INVALID_EMAIL));
    }
}
