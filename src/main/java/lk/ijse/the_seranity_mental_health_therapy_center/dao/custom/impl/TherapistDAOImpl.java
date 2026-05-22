package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public boolean add(Therapist entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Therapist entity) {
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
        Therapist entity = session.get(Therapist.class, s);
        if (entity != null) session.remove(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Therapist get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Therapist entity = session.get(Therapist.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<Therapist> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Therapist> list = session.createQuery("from Therapist", Therapist.class).list();
        session.close();
        return list;
    }
}
