package server.infrastructure.config.encoding;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import io.micronaut.context.annotation.Value;
import io.micronaut.security.token.jwt.encryption.rsa.RSAEncryptionConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Named("generator")
@Singleton
public class RSAOAEPEncryptionConfiguration implements RSAEncryptionConfiguration {
    private RSAPrivateKey rsaPrivateKey;
    private RSAPublicKey rsaPublicKey;
    JWEAlgorithm jweAlgorithm = JWEAlgorithm.RSA_OAEP_256;
    EncryptionMethod encryptionMethod = EncryptionMethod.A128GCM;

    public RSAOAEPEncryptionConfiguration(@Value("${rsa.rsa-key-pair}") String keyPairPath) {
        var keyPair = KeyPairProvider.keyPair(keyPairPath);
        if (keyPair.isPresent()) {
            this.rsaPublicKey = (RSAPublicKey) keyPair.get().getPublic();
            this.rsaPrivateKey = (RSAPrivateKey) keyPair.get().getPrivate();
        }
    }

    @Override
    public RSAPublicKey getPublicKey() {
        return this.rsaPublicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return this.rsaPrivateKey;
    }

    @Override
    public JWEAlgorithm getJweAlgorithm() {
        return this.jweAlgorithm;
    }

    @Override
    public EncryptionMethod getEncryptionMethod() {
        return this.encryptionMethod;
    }
}
