package pl.migibud.forecast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.forecast.forecastapi.ForecastClient;
import pl.migibud.location.LocationRepository;
import pl.migibud.location.LocationRepositoryMock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ForecastServiceTest {

    ForecastService forecastService;
    ObjectMapper objectMapper;
    ForecastClient forecastClient;
    LocationRepository locationRepository;
    ForecastRepository forecastRepository;

    @BeforeEach
    void setUp(){
        locationRepository = new LocationRepositoryMock();
        forecastRepository = new ForecastRepositoryMock();
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        forecastClient = new ForecastClient(objectMapper);
        forecastService = new ForecastService(locationRepository, forecastClient,forecastRepository);
    }

    @Test
    void getForecast_whenIdIsNull_throwsAnException(){
        //then
        assertThatThrownBy(()->forecastService.getForecast(null,2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }

    @Test
    void getForecast_whenThereIsNoLocationWithSuchAnId_throwsAnException(){
        //then
        assertThatThrownBy(()->forecastService.getForecast(100L,2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }

    @Test
    void getForecast_whenDayOfForecastExceedsMaxRange_throwsAnException(){
        //then
        assertThatThrownBy(()->forecastService.getForecast(1L,8))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ilość dni");
    }

    @Test
    void getForecast_whenDayOfForecastExceedsMinRange_throwsAnException(){
        //then
        assertThatThrownBy(()->forecastService.getForecast(1L,-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Ilość dni");
    }

    @Test
    void getForecast_whenAllDataAreCorrect_obtainForecastDataFromAPIAndSaveInDatabase(){
        //given
        Long locationId = 1L;
        Integer day = 2;
        //when
        Forecast forecast = forecastService.getForecast(locationId, day);
        //then
        assertThat(forecast.getId()).isNotNull();
        assertThat(forecast.getId()).isEqualTo(1L);
        assertThat(forecastService.getAll()).hasSize(1);
    }

    @Test
    void getForecast_whenAllDataAreCorrectAndForecastIsStoredInDatabase_obtainForecastDataFromCache(){
        //given
        Long locationId = 1L;
        Integer day = 2;
        //when
        Forecast forecast1 = forecastService.getForecast(locationId, day);
        Forecast forecast2 = forecastService.getForecast(locationId, day);
        //then
        assertThat(forecast1.getId()).isNotNull();
        assertThat(forecast1.getId()).isEqualTo(1L);
        assertThat(forecast2.getId()).isNotNull();
        assertThat(forecast2.getId()).isEqualTo(1L);
        assertThat(forecast1==forecast2).isTrue();
        assertThat(forecastService.getAll()).hasSize(1);
    }
}