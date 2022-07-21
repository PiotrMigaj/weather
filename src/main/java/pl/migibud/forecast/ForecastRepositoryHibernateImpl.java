package pl.migibud.forecast;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.migibud.location.Location;
import pl.migibud.location.LocationRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ForecastRepositoryHibernateImpl implements ForecastRepository {

	private final SessionFactory sessionFactory;

	@Override
	public Forecast save(Forecast forecast) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(forecast);
			transaction.commit();
			return forecast;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException("Operacja dodania pogody do bazy danych nie powiodła się!");
		}
	}

	@Override
	public Optional<Forecast> getActiveForecast(Location location, Instant forecastDate, LocalDate currentDate) {

		Transaction transaction = null;
		try(Session session = sessionFactory.openSession()){
			transaction = session.beginTransaction();
			Optional<Forecast> forecast = session.createQuery("SELECT f FROM Forecast f WHERE f.location =:location AND LocalDate.ofInstant(f.createDate, ZoneId.systemDefault()) =:createDate", Forecast.class)
					.setParameter("location", location)
					.setParameter("createDate",LocalDate.of(2022,07,20))
					.getResultList()
					.stream().findFirst();
			transaction.commit();
			return forecast;
		}catch (Exception e){
			if (transaction!=null){
				transaction.rollback();
			}
			return Optional.empty();
		}
	}
}
