package server.application.services.auth.models;

public record UserValidation(UserDto user, boolean isValid, String salt) {
}
