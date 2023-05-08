package server.domain.entities.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class Image extends BaseEntity<UUID> implements File {
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
