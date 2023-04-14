package server.application.services.auth.models.response;

import java.util.List;
import java.util.UUID;

public record DoctorResponse(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String phoneNumber,
        List<String> roles) implements UserResponse {
}
