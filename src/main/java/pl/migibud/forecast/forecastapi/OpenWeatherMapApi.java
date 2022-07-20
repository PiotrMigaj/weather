package pl.migibud.forecast.forecastapi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import pl.migibud.forecast.ForecastDataProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class OpenWeatherMapApi implements ForecastDataProvider {

	private static final String API_ID = "bcce0ca147006a7b278e974a418b3730";

	private final ObjectMapper objectMapper;

	private String getResponseJson(Integer longitude,Integer latitude){

		String uri = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s",longitude,latitude,API_ID);

		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(uri))
				.build();
		try {
			HttpResponse<String> send = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			return send.body();
		} catch (Exception e) {
			throw new IllegalArgumentException("Zły adres URI api pogodowego");
		}
	}

	private JsonNode getJsonNode(Integer longitude,Integer latitude){
		try {
			return this.objectMapper.readTree(getResponseJson(longitude,latitude));
		} catch (Exception e) {
			throw new RuntimeException("Błąd w parsowaniu odpowiedzi z serwera z pogodowym API");
		}
	}

	@Override
	public Double getTemp(Integer day,Integer longitude,Integer latitude) {
		return getJsonNode(longitude,latitude).get("daily").get(day).get("temp").get("day").asDouble();
	}

	@Override
	public Integer getPressure(Integer day,Integer longitude,Integer latitude) {
		return getJsonNode(longitude,latitude).get("daily").get(day).get("pressure").asInt();
	}

	@Override
	public Integer getHumidity(Integer day,Integer longitude,Integer latitude) {
		return getJsonNode(longitude,latitude).get("daily").get(day).get("humidity").asInt();
	}

	@Override
	public Double getWindSpeed(Integer day,Integer longitude,Integer latitude) {
		return getJsonNode(longitude,latitude).get("daily").get(day).get("wind_speed").asDouble();
	}

	@Override
	public Integer getWindDeg(Integer day,Integer longitude,Integer latitude) {
		return getJsonNode(longitude,latitude).get("daily").get(day).get("wind_deg").asInt();
	}
}
