package server.application.services.auth.models;

import server.infrastructure.utils.TokenModel;

import java.util.UUID;

public record LoginResponse (
    UUID id,
    String firstName,
    String lastName,
    String role,
    String email,
    TokenModel tokenModel
) {}
