package server.features.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static server.constants.DataConstants.FIELD_MIN_LENGTH;
import static server.constants.DataConstants.PHONE_MAX_LENGTH;
import static server.constants.DataConstants.PHONE_MIN_LENGTH;
import static server.constants.ErrorMessages.INVALID_EMAIL;
import static server.constants.ErrorMessages.MINIMUM_LENGTH_REQUIRED;
import static server.constants.ErrorMessages.PHONE_FIELD_LENGTH;
import static server.constants.ErrorMessages.REQUIRED_FIELD;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = REQUIRED_FIELD)
    @Email(message = INVALID_EMAIL)
    private String email;
    @NotBlank(message = REQUIRED_FIELD)
    @Length(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
    private String password;
    @NotBlank(message = REQUIRED_FIELD)
    @Length(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
    private String firstName;
    @NotBlank(message = REQUIRED_FIELD)
    @Length(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
    private String middleName;
    @NotBlank(message = REQUIRED_FIELD)
    @Length(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
    private String lastName;
    @NotBlank(message = REQUIRED_FIELD)
    @Length(min = PHONE_MIN_LENGTH, max = PHONE_MAX_LENGTH, message = PHONE_FIELD_LENGTH)
    private String phoneNumber;
}
