package pl.migibud.location;

import lombok.Data;

@Data
class LocationDTO {

    Long id;
    String city;
    String region;
    String country;
    Integer longitude;
    Integer latitude;

}
