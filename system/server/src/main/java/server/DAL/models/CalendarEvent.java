package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "calendar_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarEvent extends AuditInfo {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    @OneToOne
    private Appointment appointment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Schedule schedule;
}
