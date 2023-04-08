package server.application.services.auth;

import server.application.services.auth.models.UserDto;

public record ValidationDto (UserDto user, boolean isValid) {
}
