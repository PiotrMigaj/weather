package pl.migibud.forecast;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.migibud.HibernateUtils;
import pl.migibud.forecast.forecastapi.ForecastClient;
import pl.migibud.location.LocationRepositoryHibernateImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(
		name = "ForecastGetAllServlet",
		urlPatterns = {"/forecasts"}
)
public class ForecastServletGetAll extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(ForecastServletGetAll.class);

	private final ForecastService forecastService;
	private final ObjectMapper objectMapper;


	public ForecastServletGetAll() {
		this(new ForecastService(new LocationRepositoryHibernateImpl(HibernateUtils.getSessionFactory()),
				new ForecastClient(new ObjectMapper()),
				new ForecastRepositoryHibernateImpl(HibernateUtils.getSessionFactory())),
				new ObjectMapper()
				);
	}
	public ForecastServletGetAll(ForecastService forecastService, ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.forecastService = forecastService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			resp.setContentType("application/json;charset=UTF-8");
			List<ForecastDTO> forecastDTOS = forecastService.getAll().stream()
					.map(ForecastMapper::mapToForecastDTO)
					.collect(Collectors.toList());
			objectMapper.writeValue(resp.getOutputStream(), forecastDTOS);
		} catch (Exception e) {
			resp.getWriter().println(String.format("{\"errorMessage\":\"%s\"}", e.getMessage()));
		}
	}
}
