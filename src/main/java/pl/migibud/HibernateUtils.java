package pl.migibud;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {

    public static SessionFactory getSessionFactory() {
        return InstanceHolder.sessionFactory;
    }

    public static void close(){
        InstanceHolder.sessionFactory.close();
    }

    private static class InstanceHolder {
        static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        static SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
