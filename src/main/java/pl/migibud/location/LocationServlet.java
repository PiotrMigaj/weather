package pl.migibud.location;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.migibud.HibernateUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(
        name = "LocationServlet",
        urlPatterns = {"/location"}
)
public class LocationServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(LocationServlet.class);
    private final LocationService locationService;
    private final ObjectMapper objectMapper;


    public LocationServlet() {
        this(new LocationService(new LocationRepositoryHibernateImpl(HibernateUtils.getSessionFactory())), new ObjectMapper());
    }

    public LocationServlet(LocationService locationService, ObjectMapper objectMapper) {
        this.locationService = locationService;
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        List<LocationDTO> locationDTOS = locationService.getAll().stream()
                .map(LocationMapper::mapToLocationDTO)
                .collect(Collectors.toList());
        objectMapper.writeValue(resp.getOutputStream(), locationDTOS);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            resp.setContentType("application/json;charset=UTF-8");
            LocationDTO locationDTO = objectMapper.readValue(req.getInputStream(), LocationDTO.class);
            logger.info("LocationDTO: " + locationDTO);
            Location location = locationService.create(locationDTO.getCity(), locationDTO.getRegion(), locationDTO.getCountry(), locationDTO.getLongitude(), locationDTO.getLatitude());
            LocationDTO response = LocationMapper.mapToLocationDTO(location);
            objectMapper.writeValue(resp.getOutputStream(), response);
        } catch (Exception e) {
            resp.getWriter().println(String.format("{\"errorMessage\":\"%s\"}", e.getMessage()));
        }
    }
}
