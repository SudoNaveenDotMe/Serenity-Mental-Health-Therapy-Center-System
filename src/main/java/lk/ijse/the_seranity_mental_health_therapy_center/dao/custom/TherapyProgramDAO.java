package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapyProgram;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram, String> {
    long getProgramCount();
    java.util.List<Object[]> getProgramPatientDistribution();
}
