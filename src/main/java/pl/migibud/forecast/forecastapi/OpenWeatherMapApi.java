package pl.migibud.forecast.forecastapi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import pl.migibud.forecast.ForecastDTO;
import pl.migibud.forecast.ForecastDataProvider;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
public class OpenWeatherMapApi implements ForecastDataProvider {

	private static final String API_ID = "bcce0ca147006a7b278e974a418b3730"; // todo move to configuration class / file

	private final ObjectMapper objectMapper;

	@Override
	public ForecastDTO getForecastDTO(Integer day, Integer longitude, Integer latitude){
		String uri = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s",longitude,latitude,API_ID);

		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.GET()
				.uri(URI.create(uri))
				.build();

		try {
			HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
			String responseJson = httpResponse.body();
			JsonNode jsonNode = this.objectMapper.readTree(responseJson);

			return ForecastDTO.builder()
					.temperature(getTemp(jsonNode,day))
					.humidity(getHumidity(jsonNode,day))
					.pressure(getPressure(jsonNode,day))
					.windSpeed(getWindSpeed(jsonNode,day))
					.windDirection(getWindDeg(jsonNode,day))
					.build();

		} catch (Exception e) {
			throw new RuntimeException("Pobieranie pogody nie powiodło się: "+e.getMessage());
		}
	}

	private Double getTemp(JsonNode jsonNode, Integer day) {
		return jsonNode.get("daily").get(day).get("temp").get("day").asDouble();
	}

	private Integer getPressure(JsonNode jsonNode,Integer day) {
		return jsonNode.get("daily").get(day).get("pressure").asInt();
	}

	private Integer getHumidity(JsonNode jsonNode,Integer day) {
		return jsonNode.get("daily").get(day).get("humidity").asInt();
	}

	private Double getWindSpeed(JsonNode jsonNode,Integer day) {
		return jsonNode.get("daily").get(day).get("wind_speed").asDouble();
	}

	private Integer getWindDeg(JsonNode jsonNode,Integer day) {
		return jsonNode.get("daily").get(day).get("wind_deg").asInt();
	}
}
