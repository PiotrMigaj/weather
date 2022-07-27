package pl.migibud.forecast;

import pl.migibud.location.Location;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ForecastRepositoryMock implements ForecastRepository{

	private final List<Forecast> forecasts;

	public ForecastRepositoryMock() {
		this.forecasts = new ArrayList<>();
	}

	@Override
	public Forecast save(Forecast forecast) {
		forecast.setId(this.forecasts.size()+1L);
		this.forecasts.add(forecast);
		return forecast;
	}

	@Override
	public Optional<Forecast> getActiveForecast(Location location, LocalDate forecastDate) {
		Long id = location.getId();

		return this.forecasts.stream()
				.filter(f->f.getId().equals(id))
				.filter(f->f.getForecastDate().isEqual(forecastDate))
				.findFirst();
	}

	@Override
	public List<Forecast> findAll() {
		return Collections.unmodifiableList(this.forecasts);
	}
}
