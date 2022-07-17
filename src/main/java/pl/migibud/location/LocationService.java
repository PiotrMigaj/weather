package pl.migibud.location;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    Location create(String city, String region, String country, Integer longitude, Integer latitude){

        if (city==null||city.isBlank()){
            throw new IllegalArgumentException("Pole city nie może być puste");
        }
        if (region.isBlank()){
            region=null;
        }
        if (country==null||country.isBlank()){
            throw new IllegalArgumentException("Pole country nie może być puste");
        }
        if (longitude==null){
            throw new IllegalArgumentException("Pole longitude nie może być puste");
        }
        if (longitude<=-180||longitude>=180){
            throw new IllegalArgumentException("Długość geograficzna musi być w zakresie od -180 do 180");
        }
        if (latitude==null){
            throw new IllegalArgumentException("Pole latitude nie może być puste");
        }
        if (latitude<=-90||latitude>=90){
            throw new IllegalArgumentException("Szerokość geograficzna być w zakresie od -90 do 90");
        }

        Location location = Location.builder()
                .city(city)
                .region(region)
                .country(country)
                .longitude(longitude)
                .latitude(latitude)
                .build();

        return location;
    }
}
