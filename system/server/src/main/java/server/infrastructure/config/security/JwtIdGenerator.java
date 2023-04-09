package server.infrastructure.config.security;

import io.micronaut.security.authentication.Authentication;

public interface JwtIdGenerator {
    String generateJtiClaim(Authentication authentication);
}
