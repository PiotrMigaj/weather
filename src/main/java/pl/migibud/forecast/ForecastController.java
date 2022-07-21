package pl.migibud.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;
    private final ObjectMapper objectMapper;

    public String getForecast(Long id, Integer day) {
        try {
            Forecast forecast = forecastService.getForecast(id, day);
            ForecastDTO forecastDTO = mapToForecastDTO(forecast);
            return objectMapper.writeValueAsString(forecastDTO);
        } catch (Exception e) {
            return String.format("{\"errorMessage\":\"%s\"}", e.getMessage());
        }
    }

    private ForecastDTO mapToForecastDTO(Forecast forecast) {
        return ForecastDTO.builder()
                .id(forecast.getId())
                .temperature(forecast.getTemperature())
                .pressure(forecast.getPressure())
                .humidity(forecast.getHumidity())
                .windSpeed(forecast.getWindSpeed())
//                .windDirection(forecast.getWindDirection())
                .build();
    }
}
