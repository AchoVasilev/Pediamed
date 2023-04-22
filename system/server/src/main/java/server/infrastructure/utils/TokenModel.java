package server.infrastructure.utils;

public record TokenModel (
    String token,
    String expiresAt
) {}
