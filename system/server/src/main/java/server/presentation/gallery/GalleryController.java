package server.presentation.gallery;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import server.application.services.file.GalleryImageService;

@Controller(value = "/gallery")
public class GalleryController {
    private final GalleryImageService galleryImageService;

    public GalleryController(GalleryImageService galleryImageService) {
        this.galleryImageService = galleryImageService;
    }

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    public HttpResponse<?> getAll(@QueryValue int page) {
        return HttpResponse.ok(this.galleryImageService.getAll(page));
    }
}
