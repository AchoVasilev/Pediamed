package server.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events")
@Getter
@NoArgsConstructor
public class CalendarEvent extends BaseEntity<Integer> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @Setter
    @OneToOne
    private Appointment appointment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String title, Schedule schedule) {
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
        this.schedule = Guard.Against.Null(schedule);
    }
}
