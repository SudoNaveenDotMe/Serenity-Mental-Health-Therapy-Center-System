package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PatientDTO;

import java.util.List;

public interface PatientBO extends SuperBO {
    boolean savePatient(PatientDTO dto);
    boolean updatePatient(PatientDTO dto);
    boolean deletePatient(String id);
    PatientDTO getPatient(String id);
    List<PatientDTO> getAllPatients();
    List<PatientDTO> getPatientsEnrolledInAllPrograms();
    List<Object[]> getPatientsWithEnrolledPrograms();
}
