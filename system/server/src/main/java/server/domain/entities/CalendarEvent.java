package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_events")
@Getter
@NoArgsConstructor
public class CalendarEvent extends BaseEntity<Integer> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @Column(name = "schedule_id")
    private UUID scheduleId;

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String title, UUID scheduleId) {
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.scheduleId = Guard.Against.Null(scheduleId);
    }

    public void markDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
