package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CalendarEvent extends BaseEntity<UUID> {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String title;
    @Column(name = "schedule_id")
    private UUID scheduleId;

    public CalendarEvent(ZonedDateTime startDate, ZonedDateTime endDate, String title, UUID scheduleId) {
        this.id = UUID.randomUUID();
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.scheduleId = Guard.Against.Null(scheduleId);
    }

    public void markDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
