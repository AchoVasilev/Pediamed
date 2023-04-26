package server.domain.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.infrastructure.utils.guards.Guard;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "offered_services")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OfferedService extends BaseEntity<Integer> {
    private String name;
    private BigDecimal price;

    public OfferedService(String name, BigDecimal price) {
        this.name = Guard.Against.EmptyOrBlank(name);
        this.price = Guard.Against.Zero(price);
    }
}
