package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "specializations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Specialization extends BaseEntity<Integer> {
    private String name;
    private String description;
    @ManyToOne
    private Doctor doctor;
    private boolean deleted = false;
}
