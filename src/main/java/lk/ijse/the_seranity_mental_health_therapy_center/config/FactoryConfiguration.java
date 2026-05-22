package lk.ijse.the_seranity_mental_health_therapy_center.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.cfg.xml"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load hibernate.properties file", e);
        }

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.User.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.Therapist.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapyProgram.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.Payment.class);
        configuration.addAnnotatedClass(lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapySession.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
