package server.application.services.auth.models.response;

import java.util.List;
import java.util.UUID;

public record ParentResponse(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String phoneNumber,
        List<String> roles,
        List<PatientResponse> patients) implements UserResponse {
}
