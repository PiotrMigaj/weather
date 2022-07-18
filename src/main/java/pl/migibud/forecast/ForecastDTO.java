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
	float temperature;
	int humidity;
	int windSpeed;
	int pressure;
	String windDirection;
}
