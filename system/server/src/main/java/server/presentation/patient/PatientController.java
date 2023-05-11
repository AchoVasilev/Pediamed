package server.presentation.patient;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.patient.PatientService;

import java.util.UUID;

@Controller("/patient")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Get("/search")
    public HttpResponse<?> findBy(@QueryValue String query) {
        return HttpResponse.ok();
    }

    @Get("/parent/{id}")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    public HttpResponse<?> findBy(@PathVariable("id") UUID parentId) {
        var patients = this.patientService.findAllByParentId(parentId);
        return HttpResponse.ok(patients);
    }
}
