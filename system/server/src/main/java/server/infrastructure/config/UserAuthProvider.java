package server.infrastructure.config;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import server.application.services.auth.AuthService;

@Singleton
public class UserAuthProvider implements AuthenticationProvider {
    private final AuthService authService;

    public UserAuthProvider(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            var username = authenticationRequest.getIdentity().toString();
            var password = authenticationRequest.getSecret().toString();

            var validated = this.authService.validateCredentials(username, password);
            if (validated) {
                emitter.next(AuthenticationResponse.success(username));
                emitter.complete();
            } else  {
                emitter.error(AuthenticationResponse.exception());
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
