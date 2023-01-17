package server.features.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import server.utils.TokenModel;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String role;
    private String email;
    private TokenModel tokenModel;
}
