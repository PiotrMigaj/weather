package pl.migibud.forecast.forecastapi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

class RetrieveUtil {

	static <T> T retrieveResourceFromResponse(HttpResponse httpResponse,Class<T> tClass) throws IOException {
		String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(jsonResponse,tClass);
	}
}
