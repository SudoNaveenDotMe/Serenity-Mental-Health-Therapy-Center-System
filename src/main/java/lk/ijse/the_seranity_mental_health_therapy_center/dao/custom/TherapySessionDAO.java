package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapySession;

public interface TherapySessionDAO extends CrudDAO<TherapySession, String> {
    boolean isTherapistAvailable(String therapistId, java.time.LocalDate date, java.time.LocalTime time);
    long getSessionCountByDate(java.time.LocalDate date);
}
