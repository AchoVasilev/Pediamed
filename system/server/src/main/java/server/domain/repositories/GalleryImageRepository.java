package server.domain.repositories;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import server.domain.entities.GalleryImage;

import java.util.UUID;

@Repository
public interface GalleryImageRepository extends PageableRepository<GalleryImage, UUID> {
}
