package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.config.FactoryConfiguration;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) {
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
        User user = session.get(User.class, s);
        if (user != null) {
            session.remove(user);
        }
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public User get(String s) {
        Session session = FactoryConfiguration.getInstance().getSession();
        User user = session.get(User.class, s);
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<User> list = session.createQuery("from User", User.class).list();
        session.close();
        return list;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("from User where username = :un", User.class);
        query.setParameter("un", username);
        User user = query.uniqueResult();
        session.close();
        return user;
    }
}
