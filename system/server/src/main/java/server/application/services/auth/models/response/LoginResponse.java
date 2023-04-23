package server.application.services.auth.models.response;

import server.infrastructure.utils.TokenModel;

public record LoginResponse(
        UserResponse user,
        TokenModel tokenModel
) {
}
