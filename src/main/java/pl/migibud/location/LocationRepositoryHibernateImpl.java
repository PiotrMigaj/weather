package pl.migibud.location;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class LocationRepositoryHibernateImpl implements LocationRepository {

	private final SessionFactory sessionFactory;

	@Override
	public Location save(Location location) {

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.persist(location);
			transaction.commit();
			return location;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException("Operacja dodania lokalizacji do bazy danych nie powiodła się!");
		}
	}

	@Override
	public List<Location> findAll() {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			List<Location> locations = session.createQuery("FROM Location", Location.class)
					.getResultList();
			transaction.commit();
			return locations;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new RuntimeException("Operacja pobrania wszystkich lokalizacji z bazy danych nie powiodła się!");
		}
	}

	@Override
	public Optional<Location> findById(Long id) {

		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Location location = session.get(Location.class, id);
			transaction.commit();
			return Optional.ofNullable(location);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return Optional.empty();
		}
	}
}
