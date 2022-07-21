package pl.migibud.forecast;

import pl.migibud.location.Location;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

public interface ForecastRepository {
    Forecast save(Forecast location);
    Optional<Forecast> getActiveForecast(Location location, LocalDate forecastDate, Instant currentDate);
}
