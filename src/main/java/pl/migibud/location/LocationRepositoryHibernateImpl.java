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
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();


            transaction.commit();
        }catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            throw new RuntimeException("Operacja na bazie danych location nie powiodła się");
        }

        return null;
    }

    @Override
    public List<Location> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Location> findById(Long id) {
        throw new UnsupportedOperationException();
    }
}
