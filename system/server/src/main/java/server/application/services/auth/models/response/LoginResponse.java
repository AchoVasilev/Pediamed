package server.application.services.auth.models.response;

import server.infrastructure.utils.TokenModel;

import java.util.UUID;

public record LoginResponse (
    UUID id,
    TokenModel tokenModel
) {}
