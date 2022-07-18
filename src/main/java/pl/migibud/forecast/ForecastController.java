package pl.migibud.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForecastController {

	private final ObjectMapper objectMapper;
	private final ForecastService forecastService;


	public String getForecast(Long id,Integer day){

		try {
			Forecast forecast = forecastService.getForecast(id, day);
			return null;
		}catch (Exception e){
			return String.format("{\"errorMessage\":\"%s\"}", e.getMessage());
		}
	}
}
