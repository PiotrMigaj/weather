package pl.migibud.forecast;

import lombok.RequiredArgsConstructor;
import pl.migibud.location.Location;
import pl.migibud.location.LocationRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationRepository locationRepository;


    Forecast getForecast(Long locationId, Integer day){
        Optional<Location> location = locationRepository.findById(locationId);
        location.ifPresent(System.out::println);
        return null;
    }


}
