package server.application.services.auth.models;

import jakarta.validation.constraints.NotBlank;

import static server.application.constants.ErrorMessages.REQUIRED_FIELD;


public record LoginRequest (
    @NotBlank(message = REQUIRED_FIELD) String email,
    @NotBlank(message = REQUIRED_FIELD)
     String password,
     Boolean persist
){}
