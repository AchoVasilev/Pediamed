package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent extends AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @OneToOne
    private Appointment appointment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }
}
