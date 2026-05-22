package lk.ijse.the_seranity_mental_health_therapy_center.dao;

import java.util.List;

public interface CrudDAO<T, ID> extends SuperDAO {
    boolean add(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    T get(ID id);
    List<T> getAll();
}
