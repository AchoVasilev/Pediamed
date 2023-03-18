package server.application.services.auth.models;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static server.application.constants.DataConstants.FIELD_MIN_LENGTH;
import static server.application.constants.DataConstants.PHONE_MAX_LENGTH;
import static server.application.constants.DataConstants.PHONE_MIN_LENGTH;
import static server.application.constants.ErrorMessages.INVALID_EMAIL;
import static server.application.constants.ErrorMessages.MINIMUM_LENGTH_REQUIRED;
import static server.application.constants.ErrorMessages.PHONE_FIELD_LENGTH;
import static server.application.constants.ErrorMessages.REQUIRED_FIELD;

@Introspected
public record RegistrationRequest(
        @NotBlank(message = REQUIRED_FIELD)
        @Email(message = INVALID_EMAIL)
        String email,
        @NotBlank(message = REQUIRED_FIELD)
        @Size(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
        String password,
        @NotBlank(message = REQUIRED_FIELD)
        @Size(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
        String firstName,
        @NotBlank(message = REQUIRED_FIELD)
        @Size(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
        String middleName,
        @NotBlank(message = REQUIRED_FIELD)
        @Size(min = FIELD_MIN_LENGTH, message = MINIMUM_LENGTH_REQUIRED)
        String lastName,
        @NotBlank(message = REQUIRED_FIELD)
        @Size(min = PHONE_MIN_LENGTH, max = PHONE_MAX_LENGTH, message = PHONE_FIELD_LENGTH)
        String phoneNumber
) {
}
