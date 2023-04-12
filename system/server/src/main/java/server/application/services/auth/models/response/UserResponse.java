package server.application.services.auth.models.response;

import java.util.List;

public interface UserResponse {
    String firstName();
    String middleName();
    String lastName();
    String email();
    String phoneNumber();
    List<String> roles();
}
