package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PaymentDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public boolean add(Payment entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Payment entity) {
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
        Payment entity = session.get(Payment.class, s);
        if (entity != null) session.remove(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Payment get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Payment entity = session.get(Payment.class, s);
        session.close();
        return entity;
    }

    @Override
    public List<Payment> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Payment> list = session.createQuery("from Payment", Payment.class).list();
        session.close();
        return list;
    }

    @Override
    public double getGrossRevenue() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Double total = session.createQuery("select sum(p.amount) from Payment p", Double.class).uniqueResult();
        session.close();
        return total == null ? 0.0 : total;
    }
}
