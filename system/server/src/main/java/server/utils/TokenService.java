package server.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
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
}
