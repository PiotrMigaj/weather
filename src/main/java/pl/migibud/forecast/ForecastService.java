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

        Location location1 = location.get();
        System.out.println(location1);

        ForecastDTO forecastDTO = forecastDataProvider.getForecastDTO(day,location1.getLongitude(),location1.getLatitude());

        Forecast forecast = Forecast.builder()
                .temperature(forecastDTO.getTemperature())
                .pressure(forecastDTO.getPressure())
                .humidity(forecastDTO.getHumidity())
                .windSpeed(forecastDTO.getWindSpeed())
                .windDirection(forecastDTO.getWindDirection())
                .location(location1)
                .createDate(Instant.now())
                .forecastDate(Instant.now())
                .build();

        forecastRepository.save(forecast);
        return forecast;
    }
}
