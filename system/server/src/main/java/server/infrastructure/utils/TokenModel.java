package server.infrastructure.utils;

import java.util.Date;

public record TokenModel (
    String token,
    Date expiresAt
) {}
