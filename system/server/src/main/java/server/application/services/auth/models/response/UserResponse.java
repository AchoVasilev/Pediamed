package server.application.services.auth.models;

import java.util.List;

public interface UserResponse {
    String getFirstName();
    String getMiddleName();
    String getLastName();
    String getEmail();
    String getPhoneNumber();
    List<String> getRoles();
}
