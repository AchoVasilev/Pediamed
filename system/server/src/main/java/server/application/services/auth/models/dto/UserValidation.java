package server.application.services.auth.models.dto;

public record UserValidation(UserDto user, boolean isValid, String salt) {
}
