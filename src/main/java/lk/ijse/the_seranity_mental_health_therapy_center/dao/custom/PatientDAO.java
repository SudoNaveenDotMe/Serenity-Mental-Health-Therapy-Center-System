package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient;
import java.util.List;

public interface PatientDAO extends CrudDAO<Patient, String> {
    List<Patient> getPatientsEnrolledInAllPrograms();
    List<Object[]> getPatientsWithEnrolledPrograms();
    long getPatientCount();
}
