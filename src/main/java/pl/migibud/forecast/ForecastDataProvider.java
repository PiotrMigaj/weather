package pl.migibud.forecast;

public interface ForecastDataProvider {
	ForecastDTO getForecastDTO(Integer day, Integer longitude,Integer latitude);
}
