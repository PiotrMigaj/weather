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

@WebServlet(
		name = "ForecastServlet",
		urlPatterns = {"/forecast"}
)
public class ForecastServlet extends HttpServlet {

	private final Logger logger = LoggerFactory.getLogger(ForecastServlet.class);

	private final ForecastService forecastService;
	private final ObjectMapper objectMapper;


	public ForecastServlet() {
		this(new ForecastService(new LocationRepositoryHibernateImpl(HibernateUtils.getSessionFactory()),
				new ForecastClient(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)),
				new ForecastRepositoryHibernateImpl(HibernateUtils.getSessionFactory())),
				new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				);
	}
	public ForecastServlet(ForecastService forecastService, ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
		this.forecastService = forecastService;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			resp.setContentType("application/json;charset=UTF-8");
			logger.info("Got request with parameters " + req.getParameterMap());
			var id = req.getParameter("id");
			var days = req.getParameter("days");
			var idLong = Long.valueOf(id);
			var dayInteger = Integer.valueOf(days);
			Forecast forecast = forecastService.getForecast(idLong, dayInteger);
			ForecastDTO response = ForecastMapper.mapToForecastDTO(forecast);
			System.out.println(response);
			objectMapper.writeValue(resp.getOutputStream(), response);
		} catch (Exception e) {
			resp.getWriter().println(String.format("{\"errorMessage\":\"%s\"}", e.getMessage()));
		}
	}
}
