package server.presentation.patient;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.QueryValue;

@Controller("/patient")
public class PatientController {

    public HttpResponse<?> findBy(@QueryValue String query) {
        return HttpResponse.ok();
    }
}
