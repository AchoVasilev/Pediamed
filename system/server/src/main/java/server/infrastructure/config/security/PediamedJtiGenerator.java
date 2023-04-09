package server.infrastructure.config.security;

import io.micronaut.security.authentication.Authentication;
import jakarta.inject.Singleton;
import net.bytebuddy.utility.RandomString;

@Singleton
public class PediamedJtiGenerator implements JwtIdGenerator {
    @Override
    public String generateJtiClaim(Authentication authentication) {
        return authentication.getAttributes().get("salt") + this.randomString();
    }

    private String randomString() {
        return RandomString.make();
    }
}
