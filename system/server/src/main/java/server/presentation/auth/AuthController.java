package server.presentation.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.handlers.LoginHandler;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Flux;
import server.application.services.auth.AuthService;
import server.application.services.auth.models.LoginRequest;
import server.application.services.auth.models.RegistrationRequest;
import server.application.services.auth.models.UserDto;

import javax.validation.Valid;

@Controller(value = "auth")
@Secured(SecurityRule.IS_ANONYMOUS)
public class AuthController {
    private final AuthService authService;
    private final Authenticator authenticator;
    private final LoginHandler loginHandler;

    public AuthController(AuthService authService, Authenticator authenticator, LoginHandler loginHandler) {
        this.authService = authService;
        this.authenticator = authenticator;
        this.loginHandler = loginHandler;
    }

    @Post("/login")
    public HttpResponse<UserDto> login(@Body LoginRequest loginRequest, HttpRequest<?> httpRequest) {
        var credentials = new UsernamePasswordCredentials(loginRequest.email(), loginRequest.password());
        var result = Flux.from(authenticator.authenticate(httpRequest, credentials));
        var s = result.map(authenticationResponse -> authenticationResponse.getAuthentication().orElseThrow());

        var user = this.authService.findByEmail(loginRequest.email());
        return HttpResponse.ok(user);
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public HttpResponse<?> register(@Valid @Body RegistrationRequest registrationRequest) {
        this.authService.register(registrationRequest);
        return HttpResponse.created("/auth/register");
    }

//    private LoginResponse getLoginResponse(Authentication authentication, boolean persist) {
//        var user = this.authService.findByEmail(authentication.getName());
//
//        return new LoginResponse(
//                user.id(),
//                user.firstName(),
//                user.lastName(),
//                user.roleNames(),
//                user.email(),
//                this.tokenService.generateToken(authentication, persist)
//        );
//    }
}
