package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(UserDTO dto);
    boolean updateUser(UserDTO dto);
    boolean deleteUser(String id);
    UserDTO getUser(String id);
    List<UserDTO> getAllUsers();
    UserDTO loginUser(String username, String rawPassword);
}
