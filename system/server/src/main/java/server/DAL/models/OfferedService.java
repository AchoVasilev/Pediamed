package server.DAL.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.utils.guards.Guard;

import java.math.BigDecimal;

@Entity
@Table(name = "offered_services")
@NoArgsConstructor
@Getter
public class OfferedService extends BaseEntity<Integer> {
    private String name;
    private BigDecimal price;

    public OfferedService(String name, BigDecimal price) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.price = Guard.Against.Zero(price);
    }
}
