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
import server.infrastructure.config.encoding.AuthPasswordEncoderService;

import java.util.List;

@Singleton
public class UserAuthProvider implements AuthenticationProvider {
    private final AuthService authService;
    private final AuthPasswordEncoderService authPasswordEncoderService;

    public UserAuthProvider(AuthService authService, AuthPasswordEncoderService authPasswordEncoderService) {
        this.authService = authService;
        this.authPasswordEncoderService = authPasswordEncoderService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            var username = authenticationRequest.getIdentity().toString();
            var password = authenticationRequest.getSecret().toString();

            var user = this.authService.getValidatedUser(username, password);
            if (user != null) {
                emitter.next(AuthenticationResponse.success(username, List.of(user.roleId().toString())));
                emitter.complete();
            } else  {
                emitter.error(AuthenticationResponse.exception());
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}