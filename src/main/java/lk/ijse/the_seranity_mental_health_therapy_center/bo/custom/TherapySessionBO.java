package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapySessionDTO;

import java.util.List;

public interface TherapySessionBO extends SuperBO {
    boolean saveTherapySession(TherapySessionDTO dto);
    boolean updateTherapySession(TherapySessionDTO dto);
    boolean deleteTherapySession(String id);
    TherapySessionDTO getTherapySession(String id);
    List<TherapySessionDTO> getAllTherapySessions();
}
