package server.DAL.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditInfo {
    @Column(name = "date_created", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    protected Date dateCreated;

    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    protected Date dateModified;
    protected boolean deleted = false;
}
