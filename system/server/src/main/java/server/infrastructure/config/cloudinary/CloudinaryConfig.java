package server.infrastructure.config.cloudinary;

import com.cloudinary.Cloudinary;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.util.Map;

@Factory
public class CloudinaryConfig {
    private final String cloudinaryName;
    private final String cloudinaryApiKey;
    private final String cloudinarySecret;

    public CloudinaryConfig(@Value("${cloudinary.name}")String cloudinaryName, @Value("${cloudinary.apiKey}") String cloudinaryApiKey, @Value("${cloudinary.secret}") String cloudinarySecret) {
        this.cloudinaryName = cloudinaryName;
        this.cloudinaryApiKey = cloudinaryApiKey;
        this.cloudinarySecret = cloudinarySecret;
    }

    @Singleton
    public Cloudinary cloudinary () {
        var cloudinaryMap = Map.of("cloud_name", this.cloudinaryName, "api_key", this.cloudinaryApiKey, "api_secret", this.cloudinarySecret);
        return new Cloudinary(cloudinaryMap);
    }
}
