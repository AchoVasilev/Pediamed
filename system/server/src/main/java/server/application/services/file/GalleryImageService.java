package server.application.services.file;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import server.application.services.file.models.gallery.GalleryImageView;
import server.domain.repositories.GalleryImageRepository;

@Singleton
public class GalleryImageService {
    private final int PAGE_SIZE = 16;
    private final GalleryImageRepository galleryImageRepository;

    public GalleryImageService(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }

    public void upload(CompletedFileUpload file) {

    }

    public Page<GalleryImageView> getAll(int page) {
        return this.galleryImageRepository.findAll(Pageable.from(page, this.PAGE_SIZE))
                .map(i -> new GalleryImageView(i.getId(), i.getUrl(), i.getName()));
    }
}
