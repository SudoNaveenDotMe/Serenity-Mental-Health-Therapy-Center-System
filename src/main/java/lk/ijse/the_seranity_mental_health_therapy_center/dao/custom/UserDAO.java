package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.User;

public interface UserDAO extends CrudDAO<User, String> {
    User getUserByUsername(String username);
}
