package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "offered_services")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferedService extends BaseEntity<Integer> {
    private String name;
    private BigDecimal price;
}
