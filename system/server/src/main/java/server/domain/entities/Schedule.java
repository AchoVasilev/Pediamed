package server.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Schedule extends BaseEntity<UUID> {
    @OneToOne
    @JoinColumn(name = "cabinet_id", referencedColumnName = "id")
    private Cabinet cabinet;
    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(mappedBy = "schedule")
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
