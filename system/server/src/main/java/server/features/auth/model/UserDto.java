package server.features.auth.model;

import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email
) { }
