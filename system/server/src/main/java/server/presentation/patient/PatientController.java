package server.presentation.patient;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

import java.util.UUID;

@Controller("/patient")
public class PatientController {

    public HttpResponse<?> findBy(@QueryValue String query) {
        return HttpResponse.ok();
    }

    @Get("/parent/{id}")
    public HttpResponse<?> findBy(@PathVariable("id") UUID parentId) {
        return HttpResponse.ok();
    }
}
