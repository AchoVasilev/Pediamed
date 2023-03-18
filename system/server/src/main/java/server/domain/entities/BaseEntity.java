package server.domain.entities;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity<TKey> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    TKey id;
    @Column(name = "date_created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateCreated
    protected Date dateCreated;

    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    @DateUpdated
    protected Date dateModified;
    protected Boolean deleted = false;
}
