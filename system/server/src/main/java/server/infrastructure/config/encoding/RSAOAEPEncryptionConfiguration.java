package server.infrastructure.config.encoding;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import io.micronaut.context.annotation.Value;
import io.micronaut.security.token.jwt.encryption.rsa.RSAEncryptionConfiguration;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Named("generator")
@Singleton
public class RSAOAEPEncryptionConfiguration implements RSAEncryptionConfiguration {

    @Value("{rsa.public-key}")
    private String PUBLIC_KEY_PATH;
    private String PRIVATE_KEY_PATH;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;


    public RSAOAEPEncryptionConfiguration(@Value("${rsa.public-key}") String publicKey, @Value("${rsa.private-key}") String privateKey) throws IOException, GeneralSecurityException {
        this.PUBLIC_KEY_PATH = publicKey;
        this.PRIVATE_KEY_PATH = privateKey;
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream publicKeyStream = classLoader.getResourceAsStream(PUBLIC_KEY_PATH);
        InputStream privateKeyStream = classLoader.getResourceAsStream(PRIVATE_KEY_PATH);
        this.publicKey = loadPublicKey(publicKeyStream);
        this.privateKey = loadPrivateKey(privateKeyStream);
    }

    @Override
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    @Override
    public JWEAlgorithm getJweAlgorithm() {
        return JWEAlgorithm.RSA_OAEP_256;
    }

    @Override
    public EncryptionMethod getEncryptionMethod() {
        return EncryptionMethod.A128CBC_HS256;
    }

    private RSAPublicKey loadPublicKey(InputStream publicKeyStream) throws IOException, GeneralSecurityException {
        try (InputStream in = publicKeyStream) {
            Security.addProvider(new BouncyCastleProvider());
            PEMParser pemParser = new PEMParser(new InputStreamReader(in));
            SubjectPublicKeyInfo publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKeyInfo.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        }
    }

    private RSAPrivateKey loadPrivateKey(InputStream privateKeyStream) throws IOException, GeneralSecurityException {
        try (InputStream in = privateKeyStream) {
            Security.addProvider(new BouncyCastleProvider());
            PEMParser pemParser = new PEMParser(new InputStreamReader(in));
            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(privateKeyInfo.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
        }
    }
}