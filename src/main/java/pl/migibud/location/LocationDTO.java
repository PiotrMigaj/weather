package pl.migibud.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LocationDTO {

    Long id;
    String city;
    String region;
    String country;
    Integer longitude;
    Integer latitude;
}
