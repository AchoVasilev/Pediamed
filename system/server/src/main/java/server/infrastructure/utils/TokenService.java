package server.infrastructure.utils;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import io.micronaut.security.token.jwt.signature.SignatureGeneratorConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import server.infrastructure.config.security.PediamedJwtClaimsSetGenerator;

import java.text.ParseException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Singleton
@Replaces(JwtTokenGenerator.class)
@Slf4j
public class TokenService implements TokenGenerator {
    private final PediamedJwtClaimsSetGenerator pediamedJwtClaimsSetGenerator;
    protected final SignatureGeneratorConfiguration signatureConfiguration;
    protected final EncryptionConfiguration encryptionConfiguration;

    public TokenService(
                        PediamedJwtClaimsSetGenerator pediamedJwtClaimsSetGenerator,
                        @Nullable @Named("generator") SignatureGeneratorConfiguration signatureConfiguration,
                        @Nullable @Named("generator") EncryptionConfiguration encryptionConfiguration) {
        this.pediamedJwtClaimsSetGenerator = pediamedJwtClaimsSetGenerator;
        this.signatureConfiguration = signatureConfiguration;
        this.encryptionConfiguration = encryptionConfiguration;
    }

    public TokenModel generateToken(Authentication authentication) {
        var expiration = (int)authentication.getAttributes().getOrDefault("expiration", 86400);
        var claims = pediamedJwtClaimsSetGenerator.generateClaims(authentication, expiration);
        var encodedToken = this.generateToken(claims).orElseThrow();

        return new TokenModel(encodedToken, Instant.now().plusSeconds(expiration).toString());
    }

    @Override
    public Optional<String> generateToken(Authentication authentication, Integer expiration) {
        Map<String, Object> claims = pediamedJwtClaimsSetGenerator.generateClaims(authentication, expiration);
        return this.generateToken(claims);
    }

    @Override
    public Optional<String> generateToken(Map<String, Object> claims) {
        try {
            return Optional.of(this.generate(claims));
        } catch (ParseException e) {
            if (log.isWarnEnabled()) {
                log.warn("Parse exception while generating token {}", e.getMessage());
            }
        } catch (JOSEException e) {
            if (log.isWarnEnabled()) {
                log.warn("JOSEException while generating token {}", e.getMessage());
            }
        }
        return Optional.empty();
    }

    protected String generate(final Map<String, Object> claims) throws JOSEException, ParseException {
        // claims builder
        final JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

        // add claims
        for (final Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }

        return internalGenerate(builder.build());
    }

    protected String internalGenerate(final JWTClaimsSet claimsSet) throws JOSEException, ParseException {
        JWT jwt;
        // signature?
        if (this.signatureConfiguration == null) {
            jwt = new PlainJWT(claimsSet);
        } else {
            jwt = this.signatureConfiguration.sign(claimsSet);
        }

        // encryption?
        if (this.encryptionConfiguration != null) {
            return this.encryptionConfiguration.encrypt(jwt);
        } else {
            return jwt.serialize();
        }
    }
}
