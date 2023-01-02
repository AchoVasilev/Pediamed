package server.config.exceptions.handling.auth;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static server.constants.ErrorMessages.INVALID_CREDENTIALS;

@ControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(Exception ex) {
        var error = new RestError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleCredentialsExceptions(Exception ex) {
        var error = new RestError(ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(error);
    }
}
