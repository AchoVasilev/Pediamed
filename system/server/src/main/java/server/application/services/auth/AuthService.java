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
import server.application.services.role.RoleService;
import server.application.services.user.UserService;
import server.domain.entities.ApplicationUser;
import server.domain.entities.Parent;
import server.domain.entities.enums.RoleEnum;
import server.domain.valueObjects.Email;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.config.exceptions.models.EntityAlreadyExistsException;
import server.infrastructure.utils.TokenService;

import javax.transaction.Transactional;

import static server.common.ErrorMessages.EMAIL_ALREADY_EXISTS;
import static server.common.ErrorMessages.INVALID_CREDENTIALS;

@Singleton
@Slf4j
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthService(PasswordEncoder passwordEncoder, RoleService roleService, TokenService tokenService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        var user = this.userService.getByEmail(registrationRequest.email());
        if (user.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(EMAIL_ALREADY_EXISTS, registrationRequest.email()));
        }

        var role = this.roleService.findByName(RoleEnum.ROLE_PARENT);

        var newUser = new ApplicationUser(
                new Email(registrationRequest.email()),
                this.passwordEncoder.encode(registrationRequest.password()),
                registrationRequest.firstName(),
                registrationRequest.lastName(),
                new PhoneNumber(registrationRequest.phoneNumber())
        );

        newUser.getRoles().add(role);

        var parent = new Parent();
        newUser.setParent(parent);

        this.userService.save(newUser);

        log.info(String.format("User with id=%s successfully registered", newUser.getId()));
    }

    public Publisher<MutableHttpResponse<?>> authenticate(Publisher<AuthenticationResponse> auth) {
        return Flux.from(auth)
                .map(authenticationResponse -> {
                    if (authenticationResponse.isAuthenticated() && authenticationResponse.getAuthentication().isPresent()) {
                        var authentication = authenticationResponse.getAuthentication().get();
                        var loginResponse = this.getLoginResponse(authentication);
                        log.info("User logged in. [username={}, id={}]", authentication.getName(), loginResponse.user().id());

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

    @Transactional
    public void logOut(Authentication authentication) {
        var user = this.userService.getUserByEmail(authentication.getName());
        user.invalidateSalt();
        this.userService.update(user);
        log.info("User logged out [user={}, userId={}]", user.getEmail().getEmail(), user.getId());
    }

    private UserValidation validateCredentials(String username, String password) {
        var user = this.userService.getUserByEmail(username);

        return new UserValidation(
                new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail().getEmail(),
                        user.getPhoneNumber().getPhoneNumber(),
                        user.getRoles().stream().map(r -> r.getName().name()).toList()),
                this.passwordEncoder.matches(password, user.getPassword()),
                user.getSalt()
        );
    }

    private LoginResponse getLoginResponse(Authentication authentication) {
        var user = this.userService.getUser(authentication.getName());
        return new LoginResponse(
                user,
                this.tokenService.generateToken(authentication)
        );
    }
}
