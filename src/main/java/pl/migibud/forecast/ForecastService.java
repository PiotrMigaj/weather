package pl.migibud.forecast;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.migibud.forecast.forecastapi.ForecastClient;
import pl.migibud.forecast.forecastapi.ForecastClientResponse;
import pl.migibud.location.Location;
import pl.migibud.location.LocationRepository;

import java.time.*;
import java.util.Optional;

@RequiredArgsConstructor
public class ForecastService {
	private static final int MAX_OFFSET_BETWEEN_WEATHER_CHECK_IN_HOURS = 24;
	private final LocationRepository locationRepository;
	private final ForecastClient forecastClient;
	private final ForecastRepository forecastRepository;

	Forecast getForecast(Long locationId, Integer day) {
		day = Optional.ofNullable(day).orElse(1);
		Location location = locationRepository.findById(locationId).orElseThrow(() -> new IllegalArgumentException("Nie ma lokalizacji o podanym id!"));
		if (day < 1 || day > 7) {
			throw new IllegalArgumentException("Ilość dni w przód powinna być w zakresie od 1 do 7");
		}

		Optional<Forecast> activeForecastOptional = forecastRepository.getActiveForecast(location, LocalDate.now().plusDays(day));
		if (ifThereIsValidForecastInRepo(activeForecastOptional)) {
			return activeForecastOptional.get();
		}


		ForecastClientResponse forecastClientResponse = forecastClient.getForecast(day, location.getLongitude(), location.getLatitude());
		Forecast forecast = Forecast.builder()
				.temperature(forecastClientResponse.getTemperature())
				.pressure(forecastClientResponse.getPressure())
				.humidity(forecastClientResponse.getHumidity())
				.windSpeed(forecastClientResponse.getWindSpeed())
				.windDirection(forecastClientResponse.getWindDirection())
				.location(location)
				.createDate(Instant.now())
				.forecastDate(LocalDate.ofInstant(forecastClientResponse.getForecastDate(), ZoneId.of("Europe/Warsaw")))
				.build();

		return forecastRepository.save(forecast);
	}

	private boolean ifThereIsValidForecastInRepo(Optional<Forecast> activeForecastOptional) {
		return activeForecastOptional
				.filter(v -> Duration.between(v.createDate, Instant.now()).toHours() <= MAX_OFFSET_BETWEEN_WEATHER_CHECK_IN_HOURS)
				.isPresent();
	}
}
