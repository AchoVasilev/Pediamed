package server.application.services.auth.models.dto;

import java.util.List;
import java.util.UUID;

public record UserDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        List<String> roleNames
) { }
