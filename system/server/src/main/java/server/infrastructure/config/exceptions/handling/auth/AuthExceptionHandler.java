package server.infrastructure.config.exceptions.handling.auth;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.security.authentication.AuthenticationException;
import jakarta.inject.Singleton;


@Singleton
@Requires(classes = {AuthenticationException.class, ExceptionHandler.class})
public class AuthExceptionHandler implements ExceptionHandler<AuthenticationException, HttpResponse<RestError>> {

    @Override
    public HttpResponse<RestError> handle(HttpRequest request, AuthenticationException exception) {
        var restError = new RestError(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return HttpResponse.serverError(restError).status(restError.getHttpStatus());
    }
}
