package server.features.schedule.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CabinetResponse {
    private Integer id;
    private String name;
    private String city;
    private String address;
    private String postCode;
    private String phoneNumber;
}
