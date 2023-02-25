package server.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Schedule extends BaseEntity<UUID> {
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

        this.calendarEvents.add(calendarEvent);
    }

    public Integer getEventsCount() {
        return this.calendarEvents.size();
    }
}
