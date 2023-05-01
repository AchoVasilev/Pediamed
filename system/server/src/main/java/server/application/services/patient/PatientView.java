package server.application.services.patient;

import java.util.UUID;

public record PatientView(UUID id, String firstName, String lastName) {
}
