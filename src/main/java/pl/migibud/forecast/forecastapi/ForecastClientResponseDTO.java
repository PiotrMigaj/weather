package pl.migibud.forecast.forecastapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class ForecastClientResponseDTO {

    private List<SingleForecastDTO> daily;

    private static class SingleForecastDTO{
        @JsonProperty("dt")
        private Long timestamp;
        @JsonProperty("temp")
        private TemperatureDTO temperature;
        private Integer pressure;
        private Integer humidity;
        @JsonProperty("wind_speed")
        private Double windSpeed;
        @JsonProperty("wind_deg")
        private Integer windDeg;

        public static class TemperatureDTO{
            private Double day;
        }
    }
}
