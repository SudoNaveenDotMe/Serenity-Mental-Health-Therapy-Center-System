package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapySessionDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {
    @Override
    public boolean add(TherapySession entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(TherapySession entity) {
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
        TherapySession entity = session.get(TherapySession.class, s);
        if (entity != null) session.remove(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public TherapySession get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        TherapySession entity = session.get(TherapySession.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<TherapySession> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<TherapySession> list = session.createQuery("from TherapySession", TherapySession.class).list();
        session.close();
        return list;
    }

    @Override
    public boolean isTherapistAvailable(String therapistId, java.time.LocalDate date, java.time.LocalTime time) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Long count = session.createQuery("select count(t) from TherapySession t where t.therapist.id = :tId and t.sessionDate = :dt and t.sessionTime = :tm", Long.class)
                .setParameter("tId", therapistId)
                .setParameter("dt", date)
                .setParameter("tm", time)
                .uniqueResult();
        session.close();
        return count == null || count == 0;
    }

    @Override
    public long getSessionCountByDate(java.time.LocalDate date) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Long count = session.createQuery("select count(s) from TherapySession s where s.sessionDate = :date", Long.class)
                .setParameter("date", date)
                .uniqueResult();
        session.close();
        return count == null ? 0 : count;
    }
}
