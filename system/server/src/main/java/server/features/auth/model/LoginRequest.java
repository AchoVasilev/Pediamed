package server.features.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import static server.constants.ErrorMessages.REQUIRED_FIELD;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = REQUIRED_FIELD)
    private String email;
    @NotBlank(message = REQUIRED_FIELD)
    private String password;
    private Boolean persist;
}
