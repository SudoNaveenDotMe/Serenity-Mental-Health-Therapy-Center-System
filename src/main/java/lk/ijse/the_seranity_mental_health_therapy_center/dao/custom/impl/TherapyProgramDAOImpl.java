package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    @Override
    public boolean add(TherapyProgram entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(TherapyProgram entity) {
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
        TherapyProgram entity = session.get(TherapyProgram.class, s);
        if (entity != null) session.remove(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public TherapyProgram get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        TherapyProgram entity = session.get(TherapyProgram.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<TherapyProgram> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<TherapyProgram> list = session.createQuery("from TherapyProgram", TherapyProgram.class).list();
        session.close();
        return list;
    }

    @Override
    public long getProgramCount() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Long count = session.createQuery("select count(tp) from TherapyProgram tp", Long.class).uniqueResult();
        session.close();
        return count == null ? 0 : count;
    }

    @Override
    public java.util.List<Object[]> getProgramPatientDistribution() {
        Session session = FactoryConfiguration.getInstance().getSession();
        java.util.List<Object[]> list = session.createQuery(
                "select tp.name, count(r) from TherapyProgram tp left join tp.registrations r group by tp.name",
                Object[].class
        ).list();
        session.close();
        return list;
    }
}
