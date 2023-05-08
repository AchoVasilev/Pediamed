package server.domain.entities.base;

import server.infrastructure.utils.guards.Guard;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class Image extends BaseEntity<UUID> {
    protected String url;
    protected String extension;
    protected String name;

    public Image(String url, String extension, String name) {
        this.id = UUID.randomUUID();
        this.url = Guard.Against.EmptyOrBlank(url);
        this.extension = extension;
        this.name = name;
    }
}
