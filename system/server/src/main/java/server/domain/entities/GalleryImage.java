package server.domain.entities;

import server.domain.entities.base.Image;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gallery_images")
public class GalleryImage extends Image {
    public GalleryImage(String url, String extension, String name) {
        super(url, extension, name);
    }
}
