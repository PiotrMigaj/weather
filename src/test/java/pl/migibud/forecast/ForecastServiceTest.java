package pl.migibud.forecast;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.HibernateUtils;
import pl.migibud.forecast.forecastapi.ForecastClient;
import pl.migibud.location.LocationRepository;
import pl.migibud.location.LocationRepositoryMock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ForecastServiceTest {

    ForecastService forecastService;
    ObjectMapper objectMapper;
    ForecastClient forecastClient;

    @BeforeEach
    void setUp(){
        LocationRepository locationRepository = new LocationRepositoryMock();
        objectMapper = new ObjectMapper();
        forecastClient = new ForecastClient(objectMapper);
        forecastService = new ForecastService(locationRepository, forecastClient,new ForecastRepositoryHibernateImpl(HibernateUtils.getSessionFactory()));
    }

    @Test
    void getForecast_whenIdIsNull_throwsAnException(){
        //then
        assertThatThrownBy(()->forecastService.getForecast(10L,2))
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







}