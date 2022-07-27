package pl.migibud.forecast;

import pl.migibud.location.Location;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ForecastRepository {
    Forecast save(Forecast forecast);
    Optional<Forecast> getActiveForecast(Location location, LocalDate forecastDate);
    List<Forecast> findAll();
}
