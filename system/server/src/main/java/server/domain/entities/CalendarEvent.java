package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events")
@Getter
@NoArgsConstructor
public class CalendarEvent extends BaseEntity<Integer> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String title, Schedule schedule) {
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.schedule = Guard.Against.Null(schedule);
    }
}
