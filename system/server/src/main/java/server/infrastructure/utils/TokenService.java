package server.infrastructure.utils;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import io.micronaut.context.annotation.Value;
import io.micronaut.security.token.jwt.encryption.rsa.RSAEncryptionConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Named("generator")
@Singleton
public class TokenService implements RSAEncryptionConfiguration {
    private RSAPrivateKey rsaPrivateKey;
    private RSAPublicKey rsaPublicKey;
    JWEAlgorithm jweAlgorithm = JWEAlgorithm.RSA_OAEP_256;
    EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public TokenService(@Value("${rsa.rsa-public-key}") String rsaPublicKeyPath, @Value("${rsa.rsa-private-key}") String rsaPrivateKeyPath) {
        var keyPair
    }

    private final JwtEncoder jwtEncoder;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public TokenModel generateToken(Authentication authentication, boolean persist) {
        return this.generateToken(Instant.now(), authentication, persist);
    }

    private TokenModel generateToken(Instant now, Authentication authentication, boolean persist) {
        var scope = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, persist ? ChronoUnit.WEEKS : ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        var encodedToken = this.jwtEncoder.encode(JwtEncoderParameters.from(claims));
        return new TokenModel(encodedToken.getTokenValue(), encodedToken.getExpiresAt());
    }

    @Override
    public RSAPublicKey getPublicKey() {
        return null;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return null;
    }

    @Override
    public JWEAlgorithm getJweAlgorithm() {
        return null;
    }

    @Override
    public EncryptionMethod getEncryptionMethod() {
        return null;
    }
}
