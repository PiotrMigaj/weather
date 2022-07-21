package pl.migibud.forecast.forecastapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ForecastClientTest {

	ForecastClient forecastClient;
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp(){
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		forecastClient = new ForecastClient(objectMapper);
	}

	@Test
	void test(){
		ForecastClientResponse forecast = forecastClient.getForecast(7, 51, 17);


		System.out.println(forecast);
	}

}