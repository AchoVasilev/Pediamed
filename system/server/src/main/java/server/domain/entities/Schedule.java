package server.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import server.infrastructure.config.exceptions.models.EntityNotFoundException;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static server.common.ErrorMessages.MISSING_EVENT;

@Entity
@Table(name = "schedules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Schedule extends BaseEntity<UUID> {
    @OneToOne
    @JoinColumn(name = "cabinet_id", referencedColumnName = "id")
    private Cabinet cabinet;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    private List<CalendarEvent> calendarEvents = new ArrayList<>();

    public void addCalendarEvent(CalendarEvent calendarEvent) {
        var isEventPresent = this.calendarEvents
                .stream()
                .anyMatch(e -> e.getEndDate().equals(calendarEvent.getStartDate())
                        && e.getStartDate().equals(calendarEvent.getStartDate()));

        if (isEventPresent) {
            return;
        }

        this.calendarEvents.add(calendarEvent);
    }

    public Integer getEventsCount() {
        return this.calendarEvents.size();
    }

    public CalendarEvent getEventBy(int id) {
        return this.calendarEvents.stream().filter(e -> e.getId() == id && !e.getDeleted()).findFirst()
                .orElseThrow(() -> new EntityNotFoundException(MISSING_EVENT));
    }
}
