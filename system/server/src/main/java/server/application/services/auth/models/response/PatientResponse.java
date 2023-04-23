package server.application.services.auth.models.response;

public record PatientResponse(String firstName, String lastName, int age, String birthDate) {
}
