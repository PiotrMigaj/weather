package pl.migibud;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import pl.migibud.forecast.ForecastController;
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
        ForecastController forecastController = new ForecastController();
        UserInterface userInterface = new UserInterface(locationController,forecastController);
        userInterface.run();
    }
}
