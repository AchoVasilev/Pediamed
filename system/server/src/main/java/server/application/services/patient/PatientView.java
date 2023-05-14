package server.application.services.patient;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record PatientView(String id, String firstName, String lastName) {
}
