package server.presentation.auth;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import server.application.services.auth.AuthService;
import server.application.services.auth.models.LoginRequest;
import server.application.services.auth.models.RegistrationRequest;
import server.application.services.auth.models.UserDto;

import javax.validation.Valid;

@Controller(value = "auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Post("/login")
    public HttpResponse<UserDto> login(@Body LoginRequest loginRequest) {
        var user = this.authService.findByEmail(loginRequest.email());
        return HttpResponse.ok(user);
    }

    @Post("/register")
    public HttpResponse<?> register(@Valid @Body RegistrationRequest registrationRequest) {
        this.authService.register(registrationRequest);
        return HttpResponse.created("/auth/register");
    }

//    private LoginResponse getLoginResponse(Authentication authentication, boolean persist) {
//        var role = authentication.getAuthorities()
//                .stream()
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NOT_FOUND)).toString();
//
//        var user = this.authService.findByEmail(authentication.getName());
//
//        return new LoginResponse(
//                user.id(),
//                user.firstName(),
//                user.lastName(),
//                role,
//                user.email(),
//                this.tokenService.generateToken(authentication, persist)
//        );
//    }
}
