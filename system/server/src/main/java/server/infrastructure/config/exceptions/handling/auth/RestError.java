package server.infrastructure.config.exceptions.handling.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestError {
    private String message;
}
