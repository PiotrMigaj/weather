package pl.migibud.forecast.forecastapi;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastClientTest {

	/**
	 * Check out article https://www.baeldung.com/integration-testing-a-rest-api
	 */

	private static final String API_ID = ForecastClientApiId.API_ID;

	@Test
	void getWeatherDataFromAPI_whenApiIdIsNull_then401IsReceived() throws IOException {
		//given
		Integer longitude = 51;
		Integer latitude = 17;
		String uri =
				String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s", longitude,
						latitude, null);
		//when
		HttpUriRequest request = new HttpGet(uri);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		//then
		assertThat(statusCode).isEqualTo(HttpStatus.SC_UNAUTHORIZED);
	}

	@Test
	void getWeatherDataFromAPI_whenApiIdIsValid_then200IsReceived() throws IOException {
		//given
		Integer longitude = 51;
		Integer latitude = 17;
		String uri =
				String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s", longitude,
						latitude, API_ID);
		//when
		HttpUriRequest request = new HttpGet(uri);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		//then
		assertThat(statusCode).isEqualTo(HttpStatus.SC_OK);
	}

	@Test
	void getWeatherDataFromAPI_whenAllDataAreValid_thenJSONTypeIsReceived() throws IOException {
		//given
		String jsonMimeType = "application/json";
		Integer longitude = 51;
		Integer latitude = 17;
		String uri =
				String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s", longitude,
						latitude, API_ID);
		//when
		HttpUriRequest request = new HttpGet(uri);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		String mimeType = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType();
		System.out.println(mimeType);
		//then
		assertThat(mimeType).isEqualTo(jsonMimeType);
	}

	@Test
	void getWeatherDataFromAPI_whenAllDataAreValid_thenForecastClientResponseDTOIsReceived() throws IOException {
		//given
		Integer longitude = 51;
		Integer latitude = 17;
		String uri =
				String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&units=metric&exclude=hourly,minutely,alerts&appid=%s", longitude,
						latitude, API_ID);
		//when
		HttpUriRequest request = new HttpGet(uri);
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		ForecastClientResponseDTO forecastClientResponseDTO = RetrieveUtil.retrieveResourceFromResponse(httpResponse, ForecastClientResponseDTO.class);
		//then
		assertThat(forecastClientResponseDTO).isNotNull();
	}
}