package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.utils.DateTimeUtility;
import server.utils.guards.Guard;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent extends BaseEntity<Integer> {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @OneToOne
    private Appointment appointment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String title) {
        this.startDate = DateTimeUtility.validateStartDate(startDate, endDate);
        this.endDate = DateTimeUtility.validateEndDate(startDate, endDate);
        this.title = Guard.Against.EmptyOrBlank(title);
    }
}
