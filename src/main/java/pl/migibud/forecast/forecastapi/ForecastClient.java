package pl.migibud.forecast.forecastapi;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.migibud.forecast.ForecastServlet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

@RequiredArgsConstructor
public class ForecastClient {

	private static final String API_ID = ForecastClientApiId.API_ID;
	private final ObjectMapper objectMapper;
	public ForecastClientResponse getForecast(Integer day, Integer longitude, Integer latitude){
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String uri = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s",longitude,latitude,API_ID);

		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(uri))
				.build();

		try {
			HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			String responseJson = httpResponse.body();
			ForecastClientResponseDTO forecastClientResponseDTO = objectMapper.readValue(responseJson, ForecastClientResponseDTO.class);
			return mapToForecastClientResponse(day, forecastClientResponseDTO);
		} catch (Exception e) {
			throw new RuntimeException("Pobieranie pogody nie powiodło się: "+e.getMessage());
		}
	}

	private ForecastClientResponse mapToForecastClientResponse(Integer day, ForecastClientResponseDTO forecastClientResponseDTO){

		ForecastClientResponseDTO.SingleForecastDTO singleForecastDTO = forecastClientResponseDTO.getDaily().get(day);

		return ForecastClientResponse.builder()
				.temperature(singleForecastDTO.getTemperature().getDay())
				.pressure(singleForecastDTO.getPressure())
				.humidity(singleForecastDTO.getHumidity())
				.windSpeed(singleForecastDTO.getWindSpeed())
				.windDirection(singleForecastDTO.getWindDeg())
				.forecastDate(Instant.ofEpochSecond(singleForecastDTO.getTimestamp()))
				.build();
	}
}
