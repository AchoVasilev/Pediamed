package server.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import server.domain.valueObjects.PhoneNumber;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "cabinets")
public class Cabinet extends BaseEntity<Integer> {
    private String name;
    private String city;
    private String address;
    private String postCode;
    @Embedded
    private PhoneNumber phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cabinet")
    private Schedule schedule;
    @ManyToOne
    private Doctor doctor;

    @Convert(converter = StringListConverter.class)
    private List<String> workDays;

    public Cabinet(String name, String city, String address, String postCode, PhoneNumber phoneNumber, Schedule schedule, Doctor doctor) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.city = Guard.Against.EmptyOrBlank(city);
        this.address = Guard.Against.EmptyOrBlank(address);
        this.postCode = Guard.Against.EmptyOrBlank(postCode);
        this.phoneNumber = phoneNumber;
        this.schedule = Guard.Against.Null(schedule);
        this.doctor = Guard.Against.Null(doctor);
        this.workDays = new ArrayList<>();
    }

    @Converter
    public static class StringListConverter implements AttributeConverter<List<String>, String> {

        @Override
        public String convertToDatabaseColumn(List<String> input) {
            return input != null && input.size() > 0 ? String.join(", ", input) : "";
        }

        @Override
        public List<String> convertToEntityAttribute(String dbData) {
            return dbData != null && !dbData.equals("") ? List.of(dbData.split(",\\s")) : Collections.emptyList();
        }
    }
}
