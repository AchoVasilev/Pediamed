package server.application.services.auth.models.request;

import javax.validation.constraints.NotBlank;

import static server.common.ErrorMessages.REQUIRED_FIELD;


public record LoginRequest (
    @NotBlank(message = REQUIRED_FIELD) String email,
    @NotBlank(message = REQUIRED_FIELD)
    String password,
    Boolean persist
){}
