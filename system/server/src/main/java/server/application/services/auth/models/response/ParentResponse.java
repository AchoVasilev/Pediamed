package server.application.services.auth.models.response;

import lombok.Getter;

import java.util.List;

@Getter
public record ParentResponse(String firstName,
                             String middleName,
                             String lastName,
                             String email,
                             String phoneNumber,
                             List<String> roles,
                             List<PatientResponse> patients) implements UserResponse {
}
