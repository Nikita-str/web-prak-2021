package utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            try {
                System.out.println(registry);
                MetadataSources mdata_s = new MetadataSources(registry);
                System.out.println(mdata_s);
                Metadata mdata = mdata_s.buildMetadata();
                sessionFactory = mdata.buildSessionFactory();
            } catch (Exception ex) {
                StandardServiceRegistryBuilder.destroy(registry);
                System.err.println(ex);
            }
        }
        return sessionFactory;
    }
}