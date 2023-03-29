package server.infrastructure.config.security;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class PediamedJwtClaimsSetGenerator extends JWTClaimsSetGenerator {
    /**
     * @param tokenConfiguration       Token Configuration
     * @param jwtIdGenerator           Generator which creates unique JWT ID
     * @param claimsAudienceProvider   Provider which identifies the recipients that the JWT is intended for.
     * @param applicationConfiguration The application configuration
     */
    public PediamedJwtClaimsSetGenerator(TokenConfiguration tokenConfiguration, JwtIdGenerator jwtIdGenerator, ClaimsAudienceProvider claimsAudienceProvider, ApplicationConfiguration applicationConfiguration) {
        super(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration);
    }

    @Override
    public Map<String, Object> generateClaims(Authentication authentication, Integer expiration) {
        expiration = (int)authentication.getAttributes().getOrDefault("expiration", 86400);
        return super.generateClaims(authentication, expiration);
    }
}
