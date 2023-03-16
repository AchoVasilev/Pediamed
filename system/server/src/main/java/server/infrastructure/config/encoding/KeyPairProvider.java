package server.infrastructure.config.encoding;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.Security;
import java.util.Optional;

@Slf4j
public class KeyPairProvider {
    public static Optional<KeyPair> keyPair(String pemPath) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            var pemParser = new PEMParser(new InputStreamReader(Files.newInputStream(Paths.get(pemPath))));
            var pemKeyPair = (PEMKeyPair) pemParser.readObject();

            // Convert to Java (JCA) format
            var converter = new JcaPEMKeyConverter();
            var keyPair = converter.getKeyPair(pemKeyPair);
            pemParser.close();

            return Optional.of(keyPair);
        } catch (FileNotFoundException e) {
            log.warn("file not found: {}", pemPath);
        } catch (PEMException e) {
            log.warn("PEMException {}", e.getMessage());
        } catch (IOException e) {
            log.warn("IOException {}", e.getMessage());
        }

        return Optional.empty();
    }
}
