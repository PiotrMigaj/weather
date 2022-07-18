package pl.migibud.forecast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.location.LocationRepository;
import pl.migibud.location.LocationRepositoryMock;

import static org.junit.jupiter.api.Assertions.*;

class ForecastServiceTest {

    ForecastService forecastService;

    @BeforeEach
    void setUp(){
        LocationRepository locationRepository = new LocationRepositoryMock();
        forecastService = new ForecastService(locationRepository);
    }

    @Test
    void


}