package server.infrastructure.utils;

import java.time.Instant;

public record TokenModel (
    String token,
    Instant expiresAt
) {}