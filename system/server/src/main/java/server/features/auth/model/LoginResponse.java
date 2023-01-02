package server.features.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import server.utils.TokenModel;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private TokenModel tokenModel;
    private String role;
    private String email;
}
