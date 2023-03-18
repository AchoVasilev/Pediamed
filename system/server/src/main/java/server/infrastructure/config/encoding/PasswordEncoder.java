package server.infrastructure.config.encoding;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

public interface PasswordEncoder {
    String encode(@NotBlank @NonNull String rawPassword);

    boolean matches(@NotBlank @NonNull String rawPassword, @NotBlank @NonNull String encodedPassword);
}
