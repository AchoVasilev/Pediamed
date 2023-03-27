package server.infrastructure.config;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import server.application.services.auth.AuthService;
import server.application.services.auth.models.LoginRequest;

import java.util.List;

@Singleton
public class UserAuthProvider implements AuthenticationProvider {
    private final AuthService authService;

    public UserAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.create(emitter -> {
            var req = httpRequest.getBody(LoginRequest.class);
            var username = authenticationRequest.getIdentity().toString();
            var password = authenticationRequest.getSecret().toString();
            var user = this.authService.getValidatedUser(username, password);
            if (user != null) {
                emitter.success(AuthenticationResponse.success(username, List.of(user.roleNames().toString())));
            } else  {
                emitter.error(AuthenticationResponse.exception());
            }
        });
    }
}
