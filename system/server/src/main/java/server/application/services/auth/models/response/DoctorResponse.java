package server.application.services.auth.models.response;

import java.util.List;

public record DoctorResponse(String firstName,
                             String middleName,
                             String lastName,
                             String email,
                             String phoneNumber,
                             List<String> roles) implements UserResponse{
}
