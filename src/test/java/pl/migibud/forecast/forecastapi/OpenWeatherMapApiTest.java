package pl.migibud.forecast.forecastapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.forecast.ForecastDataProvider;

class OpenWeatherMapApiTest {

	ForecastDataProvider forecastDataProvider;

	@BeforeEach
	void setUp(){
		forecastDataProvider = new OpenWeatherMapApi(new ObjectMapper());
	}

	@Test
	void test(){

		Integer humidity = forecastDataProvider.getHumidity(2, 51, 17);
		System.out.println(humidity);
	}

}