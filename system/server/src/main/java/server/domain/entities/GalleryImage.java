package server.domain.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import server.domain.entities.base.Image;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gallery_images")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class GalleryImage extends Image {
    public GalleryImage(String url, String extension, String name) {
        super(url, extension, name);
    }
}
