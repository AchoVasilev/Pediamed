package server.features.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import server.features.auth.model.LoginRequest;
import server.features.auth.model.RegistrationRequest;
import server.utils.TokenService;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        var authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        return ResponseEntity.ok(this.tokenService.generateToken(authentication, loginRequest.getPersist()));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        var userId = this.authService.register(registrationRequest);
        return new ResponseEntity<>(userId,HttpStatus.CREATED);
    }
}
