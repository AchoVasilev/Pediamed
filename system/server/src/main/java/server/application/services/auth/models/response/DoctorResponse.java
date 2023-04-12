package server.application.services.auth.models.response;

import lombok.Getter;

import java.util.List;

@Getter
public record DoctorResponse(String firstName,
                             String middleName,
                             String lastName,
                             String email,
                             String phoneNumber,
                             List<String> roles) implements UserResponse{
}
