package pl.migibud;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import pl.migibud.forecast.*;
import pl.migibud.forecast.forecastapi.OpenWeatherMapApi;
import pl.migibud.location.LocationController;
import pl.migibud.location.LocationRepository;
import pl.migibud.location.LocationRepositoryHibernateImpl;
import pl.migibud.location.LocationService;

public class ConsoleApp {
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        LocationRepository locationRepository = new LocationRepositoryHibernateImpl(sessionFactory);
        LocationService locationService = new LocationService(locationRepository);
        LocationController locationController = new LocationController(objectMapper,locationService);
        ForecastDataProvider forecastDataProvider = new OpenWeatherMapApi(objectMapper);
        ForecastRepository forecastRepository = new ForecastRepositoryHibernateImpl(sessionFactory);
        ForecastService forecastService = new ForecastService(locationRepository,forecastDataProvider,forecastRepository);
        ForecastController forecastController = new ForecastController(forecastService);
        UserInterface userInterface = new UserInterface(locationController,forecastController);
        userInterface.run();
    }
}
