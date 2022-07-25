package pl.migibud.forecast;

public class ForecastMapper {

	private ForecastMapper(){}

	public static ForecastDTO mapToForecastDTO(Forecast forecast) {
		return ForecastDTO.builder()
				.id(forecast.getId())
				.temperature(forecast.getTemperature())
				.pressure(forecast.getPressure())
				.humidity(forecast.getHumidity())
				.windSpeed(forecast.getWindSpeed())
				.windDirection(mapWindDegToCompassDirection(forecast.getWindDirection()))
				.build();
	}

	private static String mapWindDegToCompassDirection(int windDeg){
		String[] compassDir = {"N","NE","E","SE","S","SW","W","NW","N"};
		int index = (int)Math.round(windDeg/45.0);
		return compassDir[index];
	}

}
