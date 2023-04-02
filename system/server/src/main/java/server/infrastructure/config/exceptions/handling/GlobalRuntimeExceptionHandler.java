package server.infrastructure.config.exceptions.handling;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import server.infrastructure.config.exceptions.handling.auth.RestError;

@Singleton
@Requires(classes = {RuntimeException.class, ExceptionHandler.class})
public class GlobalRuntimeExceptionHandler implements ExceptionHandler<RuntimeException, HttpResponse<RestError>> {

    @Override
    public HttpResponse<RestError> handle(HttpRequest request, RuntimeException exception) {
        var restError = new RestError(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return HttpResponse.serverError(restError).status(restError.getHttpStatus());
    }
}
