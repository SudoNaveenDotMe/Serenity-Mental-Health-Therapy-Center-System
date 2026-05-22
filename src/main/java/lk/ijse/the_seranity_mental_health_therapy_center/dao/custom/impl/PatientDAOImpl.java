package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean add(Patient entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Patient entity) {
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
        Patient entity = session.get(Patient.class, s);
        if (entity != null) {
            session.remove(entity);
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Patient get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Patient entity = session.get(Patient.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<Patient> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Patient> list = session.createQuery("from Patient", Patient.class).list();
        session.close();
        return list;
    }

    @Override
    public List<Patient> getPatientsEnrolledInAllPrograms() {
        // HQL query to retrieve patients who have registered for every available therapy program
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "select p from Patient p where not exists " +
                "(select tp from TherapyProgram tp where not exists " +
                "(select r from Registration r where r.patient = p and r.program = tp))";
        List<Patient> list = session.createQuery(hql, Patient.class).list();
        session.close();
        return list;
    }

    @Override
    public List<Object[]> getPatientsWithEnrolledPrograms() {
        // Retrieve Patients Along with Their Enrolled Therapy Programs
        Session session = FactoryConfiguration.getInstance().getSession();
        String hql = "select p.name, tp.name from Patient p " +
                "join p.registrations r join r.program tp";
        List<Object[]> list = session.createQuery(hql, Object[].class).list();
        session.close();
        return list;
    }

    @Override
    public long getPatientCount() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Long count = session.createQuery("select count(p) from Patient p", Long.class).uniqueResult();
        session.close();
        return count == null ? 0 : count;
    }
}
