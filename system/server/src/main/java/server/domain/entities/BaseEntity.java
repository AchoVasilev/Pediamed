package server.domain.entities;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity<TKey> implements Serializable {
    @Id
    protected TKey id;
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
