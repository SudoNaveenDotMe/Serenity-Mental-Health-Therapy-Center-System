package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.RegistrationDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {
    @Override
    public boolean add(Registration entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Registration entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Registration entity = session.get(Registration.class, s);
        if (entity != null) session.remove(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Registration get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Registration entity = session.get(Registration.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<Registration> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Registration> list = session.createQuery("from Registration", Registration.class).list();
        session.close();
        return list;
    }

    @Override
    public List<Registration> getRecentRegistrations(int limit) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Registration> list = session.createQuery(
                "select r from Registration r join fetch r.patient join fetch r.program order by r.registrationDate desc, r.id desc",
                Registration.class
        ).setMaxResults(limit).list();
        session.close();
        return list;
    }
}
