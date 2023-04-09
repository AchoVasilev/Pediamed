package server.infrastructure.config.security;

import io.micronaut.context.annotation.Value;
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
import java.util.Map;

import static server.common.ErrorMessages.INVALID_CREDENTIALS;

@Singleton
public class UserAuthProvider implements AuthenticationProvider {
    private final AuthService authService;
    private final int shortTokenExpiration;
    private final int longTokenExpiration;
    public UserAuthProvider(
            AuthService authService,
            @Value("${micronaut.security.token.jwt.signatures.secret.generator.access-token.expiration}") int shortTokenExpiration,
            @Value("${micronaut.security.token.jwt.signatures.secret.generator.access-token.long-expiration}") int longTokenExpiration) {
        this.authService = authService;
        this.shortTokenExpiration = shortTokenExpiration;
        this.longTokenExpiration = longTokenExpiration;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Mono.create(emitter -> {
            var username = authenticationRequest.getIdentity().toString();
            var password = authenticationRequest.getSecret().toString();
            var user = this.authService.getValidatedUser(username, password);
            if (user != null) {
                var body = httpRequest.getBody(LoginRequest.class);
                var expiration = body.isPresent() && body.get().persist() ? this.longTokenExpiration : this.shortTokenExpiration;
                emitter.success(AuthenticationResponse.success(username,
                        List.of(user.user().roleNames().toString()),
                        Map.of("expiration", expiration, "salt", user.salt())));
            } else  {
                emitter.error(AuthenticationResponse.exception(INVALID_CREDENTIALS));
            }
        });
    }
}
