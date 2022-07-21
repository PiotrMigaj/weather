package pl.migibud.forecast;

import lombok.RequiredArgsConstructor;
import pl.migibud.forecast.forecastapi.ForecastClient;
import pl.migibud.forecast.forecastapi.ForecastClientResponse;
import pl.migibud.location.Location;
import pl.migibud.location.LocationRepository;

import java.time.*;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastService {

    private final LocationRepository locationRepository;
    private final ForecastClient forecastClient;
    private final ForecastRepository forecastRepository;

    Forecast getForecast(Long locationId, Integer day){
        day = Optional.ofNullable(day).orElse(1);
        Location location = locationRepository.findById(locationId).orElseThrow(()->new IllegalArgumentException("Nie ma lokalizacji o podanym id!"));
        if(day<1||day>7){
            throw new IllegalArgumentException("Ilość dni w przód powinna być w zakresie od 1 do 7");
        }
        ForecastClientResponse forecastClientResponse = forecastClient.getForecast(day,location.getLongitude(),location.getLatitude());
        Forecast forecast = Forecast.builder()
                .temperature(forecastClientResponse.getTemperature())
                .pressure(forecastClientResponse.getPressure())
                .humidity(forecastClientResponse.getHumidity())
                .windSpeed(forecastClientResponse.getWindSpeed())
                .windDirection(forecastClientResponse.getWindDirection())
                .location(location)
                .createDate(Instant.now())
                .forecastDate(forecastClientResponse.getForecastDate())
                .build();

        Forecast save = forecastRepository.save(forecast);
//        Forecast forecast1 = forecastRepository.getActiveForecast(location, getForecastInstant(day), LocalDate.now()).get();
//        System.out.println(forecast1);
        return save;
    }

    private Instant getForecastInstant(Integer day){

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now().plusDays(day),LocalTime.of(12,0,0));
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
