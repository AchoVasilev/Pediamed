package server.features.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import server.config.exceptions.models.EntityNotFoundException;
import server.features.auth.model.LoginRequest;
import server.features.auth.model.LoginResponse;
import server.features.auth.model.RegistrationRequest;
import server.utils.TokenService;

import static server.constants.ErrorMessages.ENTITY_NOT_FOUND;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, AuthService authService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return ResponseEntity.ok(this.getLoginResponse(authentication, loginRequest.getPersist()));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        this.authService.register(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private LoginResponse getLoginResponse(Authentication authentication, boolean persist) {
        var role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND)).toString();

        return new LoginResponse(
                this.tokenService.generateToken(authentication, persist),
                role,
                authentication.getName()
        );
    }
}
