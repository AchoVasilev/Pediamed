package server.application.services.file.models.gallery;

import server.domain.entities.base.File;

import java.util.UUID;

public record GalleryImageView (UUID id, String url, String name) implements File {
}
