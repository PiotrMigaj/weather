package pl.migibud.forecast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ForecastDTO {

	Long id;
	double temperature;
	int humidity;
	double windSpeed;
	int pressure;
	int windDirection;
}
