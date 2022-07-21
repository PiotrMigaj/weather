package pl.migibud.forecast.forecastapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastClientResponse {

	Double temperature;
	Integer pressure;
	Integer humidity;
	Double windSpeed;
	Integer windDirection;
	Instant forecastDate;
}
