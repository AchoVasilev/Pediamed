package server.application.services.offeredService;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class OfferedServiceView {
    private Integer id;
    private String name;
    private BigDecimal price;
}
