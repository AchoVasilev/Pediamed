package server.DAL.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @OneToOne
    private Cabinet cabinet;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<CalendarEvent> calendarEvents = new ArrayList<>();

    public void addCalendarEvent(CalendarEvent calendarEvent) {
        var isEventPresent = this.calendarEvents
                .stream()
                .anyMatch(e -> e.getEndDate() == calendarEvent.getStartDate()
                        && e.getStartDate() == calendarEvent.getStartDate());

        if (isEventPresent) {
            return;
        }

        calendarEvent.setSchedule(this);

        this.calendarEvents.add(calendarEvent);
    }

    public Integer getEventsCount() {
        return this.calendarEvents.size();
    }
}
