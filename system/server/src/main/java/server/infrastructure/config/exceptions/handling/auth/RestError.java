package server.infrastructure.config.exceptions.handling.auth;

import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestError {
    private String message;
    private HttpStatus httpStatus;
}
