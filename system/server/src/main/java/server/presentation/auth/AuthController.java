package server.presentation.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import server.application.services.auth.AuthService;
import server.application.services.auth.models.LoginRequest;
import server.application.services.auth.models.LoginResponse;
import server.application.services.auth.models.RegistrationRequest;
import server.infrastructure.utils.TokenService;

import javax.validation.Valid;

@Controller(value = "auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {
    private final AuthService authService;
    private final Authenticator authenticator;
    private final TokenService tokenService;

    public AuthController(AuthService authService, Authenticator authenticator, TokenService tokenService) {
        this.authService = authService;
        this.authenticator = authenticator;
        this.tokenService = tokenService;
    }

    @Post("/login")
    public Publisher<MutableHttpResponse<?>> login(@Body LoginRequest loginRequest, HttpRequest<?> httpRequest) {
        var credentials = new UsernamePasswordCredentials(loginRequest.email(), loginRequest.password());
        return Flux.from(authenticator.authenticate(httpRequest, credentials))
                .map(authenticationResponse -> {
                    if (authenticationResponse.isAuthenticated() && authenticationResponse.getAuthentication().isPresent()) {
                        Authentication authentication = authenticationResponse.getAuthentication().get();
                        return HttpResponse.ok(this.getLoginResponse(authentication));
                    } else {
                        return HttpResponse.badRequest();
                    }
                }).switchIfEmpty(Mono.defer(() -> Mono.just(HttpResponse.status(HttpStatus.UNAUTHORIZED))));
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public HttpResponse<?> register(@Valid @Body RegistrationRequest registrationRequest) {
        this.authService.register(registrationRequest);
        return HttpResponse.created("/auth/register");
    }

    private LoginResponse getLoginResponse(Authentication authentication) {
        var user = this.authService.findByEmail(authentication.getName());
        return new LoginResponse(
                user.id(),
                user.firstName(),
                user.lastName(),
                user.roleNames(),
                user.email(),
                this.tokenService.generateToken(authentication)
        );
    }
}
