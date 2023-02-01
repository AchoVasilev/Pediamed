package server.features.cabinet.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CabinetResponse {
    private Integer id;
    private String name;
    private String city;
}
