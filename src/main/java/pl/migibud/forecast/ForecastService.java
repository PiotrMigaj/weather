package pl.migibud.forecast;

import lombok.RequiredArgsConstructor;
import pl.migibud.location.Location;
import pl.migibud.location.LocationRepository;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationRepository locationRepository;
    private final ForecastDataProvider forecastDataProvider;
    private final ForecastRepository forecastRepository;

    Forecast getForecast(Long locationId, Integer day){
        day = Optional.ofNullable(day).orElse(0);
        Optional<Location> location = locationRepository.findById(locationId);
        if (location.isEmpty()){
            throw new IllegalArgumentException("Nie ma lokalizacji o podanym id!");
        }
        if(day<0||day>7){
            throw new IllegalArgumentException("Ilość dni w przód powinna być w zakresie od 0 do 7");
        }

        Double temp = forecastDataProvider.getTemp(day, location.get().getLongitude(), location.get().getLatitude());
        System.out.println(temp);
        Integer pressure = forecastDataProvider.getPressure(day, location.get().getLongitude(), location.get().getLatitude());
        System.out.println(pressure);
        Integer humidity = forecastDataProvider.getHumidity(day, location.get().getLongitude(), location.get().getLatitude());
        System.out.println(humidity);
        Double windSpeed = forecastDataProvider.getWindSpeed(day, location.get().getLongitude(), location.get().getLatitude());
        System.out.println(windSpeed);
        Integer windDeg = forecastDataProvider.getWindDeg(day, location.get().getLongitude(), location.get().getLatitude());
        System.out.println(windDeg);

        Location location1 = location.get();
        System.out.println(location1);

        Forecast forecast = Forecast.builder()
                .temperature(temp)
                .pressure(pressure)
                .humidity(humidity)
                .windSpeed(windSpeed)
                .windDirection(windDeg)
                .location(location1)
                .createDate(Instant.now())
                .forecastDate(Instant.now())
                .build();

        forecastRepository.save(forecast);




        return null;
    }


}
