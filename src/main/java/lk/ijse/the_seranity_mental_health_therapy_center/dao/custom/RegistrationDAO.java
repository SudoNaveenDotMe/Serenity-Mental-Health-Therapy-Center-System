package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration;

public interface RegistrationDAO extends CrudDAO<Registration, String> {
    java.util.List<Registration> getRecentRegistrations(int limit);
}
