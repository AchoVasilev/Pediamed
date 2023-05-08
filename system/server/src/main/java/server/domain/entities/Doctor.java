package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.entities.base.BaseEntity;
import server.infrastructure.utils.DateTimeUtility;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static server.common.Constants.SOFIA_TIMEZONE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Table(name = "doctors")
public class Doctor extends BaseEntity<UUID> {
    private String biography;
    private int yearsOfExperience;
    private int age;
    private String birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_user_id", referencedColumnName = "id")
    private ApplicationUser applicationUser;
    @OneToMany(mappedBy = "doctor")
    private List<Specialization> specializations = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Cabinet> cabinets = new ArrayList<>();

    public Doctor(String biography, int yearsOfExperience, String birthDate, ApplicationUser applicationUser) {
        this.id = UUID.randomUUID();
        this.biography = Guard.Against.EmptyOrBlank(biography);
        this.yearsOfExperience = Guard.Against.Zero(yearsOfExperience);
        this.birthDate = birthDate;
        this.applicationUser = applicationUser;
        this.age = this.calculateAge();
    }

    private int calculateAge() {
        var date = DateTimeUtility.parseDate(this.birthDate);
        var now = ZonedDateTime.now(ZoneId.of(SOFIA_TIMEZONE));
        var period = Period.between(date.toLocalDate(), now.toLocalDate());
        return Math.max(period.getYears(), 0);
    }
}
