package server.presentation.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.authentication.Authenticator;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import server.application.services.auth.AuthService;
import server.application.services.auth.models.LoginRequest;
import server.application.services.auth.models.RegistrationRequest;

import javax.validation.Valid;

@Controller(value = "auth")
@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final Authenticator authenticator;

    public AuthController(AuthService authService, Authenticator authenticator) {
        this.authService = authService;
        this.authenticator = authenticator;
    }

    @Post("/login")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public Publisher<MutableHttpResponse<?>> login(@Body LoginRequest loginRequest, HttpRequest<?> httpRequest) {
        var credentials = new UsernamePasswordCredentials(loginRequest.email(), loginRequest.password());
        var authenticationResponse = this.authenticator.authenticate(httpRequest, credentials);
        return this.authService.authenticate(authenticationResponse);
    }

    @Post("/register")
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> register(@Valid @Body RegistrationRequest registrationRequest) {
        this.authService.register(registrationRequest);
        return HttpResponse.ok();
    }

    @Post("/logout")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> logout(Authentication authentication) {
        this.authService.logOut(authentication);
        return HttpResponse.ok();
    }
}
