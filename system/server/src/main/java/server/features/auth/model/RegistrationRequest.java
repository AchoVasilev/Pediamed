package server.features.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static server.constants.DataConstants.PHONE_MAX_LENGTH;
import static server.constants.DataConstants.PHONE_MIN_LENGTH;
import static server.constants.ErrorMessages.INVALID_EMAIL;
import static server.constants.ErrorMessages.REQUIRED_FIELD;
import static server.constants.DataConstants.FIELD_MIN_LENGTH;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = REQUIRED_FIELD)
    @Email(message = INVALID_EMAIL)
    private String email;
    @Min(value = FIELD_MIN_LENGTH)
    private String password;
    @NotBlank(message = REQUIRED_FIELD)
    @Min(value = FIELD_MIN_LENGTH)
    private String firstName;
    @NotBlank(message = REQUIRED_FIELD)
    @Min(value = FIELD_MIN_LENGTH)
    private String middleName;
    @NotBlank(message = REQUIRED_FIELD)
    @Min(value = FIELD_MIN_LENGTH)
    private String lastName;
    @NotBlank(message = REQUIRED_FIELD)
    @Min(value = PHONE_MIN_LENGTH)
    @Max(value = PHONE_MAX_LENGTH)
    private String phoneNumber;
}
