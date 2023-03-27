package server.application.services.auth.models;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        List<String> roleNames
) { }
