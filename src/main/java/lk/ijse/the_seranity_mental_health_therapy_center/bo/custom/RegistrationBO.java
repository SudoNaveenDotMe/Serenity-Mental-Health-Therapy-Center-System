package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    boolean saveRegistration(RegistrationDTO dto);
    boolean updateRegistration(RegistrationDTO dto);
    boolean deleteRegistration(String id);
    RegistrationDTO getRegistration(String id);
    List<RegistrationDTO> getAllRegistrations();
}
