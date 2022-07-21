package pl.migibud.forecast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForecastDTO {

	Long id;
	double temperature;
	int humidity;
	double windSpeed;
	int pressure;
	String windDirection; // todo use String (N, S, W, E, NW....)
}
