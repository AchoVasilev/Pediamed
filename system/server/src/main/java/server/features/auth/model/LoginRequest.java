package server.features.auth.model;

import jakarta.validation.constraints.NotBlank;

import static server.constants.ErrorMessages.REQUIRED_FIELD;


public record LoginRequest (
    @NotBlank(message = REQUIRED_FIELD) String email,
    @NotBlank(message = REQUIRED_FIELD)
     String password,
     Boolean persist
){}
