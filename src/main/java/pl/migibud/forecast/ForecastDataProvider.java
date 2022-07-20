package pl.migibud.forecast;

public interface ForecastDataProvider {
	Double getTemp(Integer day,Integer longitude,Integer latitude);
	Integer getPressure(Integer day,Integer longitude,Integer latitude);
	Integer getHumidity(Integer day,Integer longitude,Integer latitude);
	Double getWindSpeed(Integer day,Integer longitude,Integer latitude);
	Integer getWindDeg(Integer day,Integer longitude,Integer latitude);
}
