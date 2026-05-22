package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.UserDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.User;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.LoginException;
import lk.ijse.the_seranity_mental_health_therapy_center.util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean saveUser(UserDTO dto) {
        String hashedPassword = PasswordUtil.hashPassword(dto.getPassword());
        return userDAO.add(new User(dto.getId(), dto.getUsername(), hashedPassword, dto.getRole()));
    }

    @Override
    public boolean updateUser(UserDTO dto) {
        String hashedPassword = PasswordUtil.hashPassword(dto.getPassword());
        return userDAO.update(new User(dto.getId(), dto.getUsername(), hashedPassword, dto.getRole()));
    }

    @Override
    public boolean deleteUser(String id) {
        return userDAO.delete(id);
    }

    @Override
    public UserDTO getUser(String id) {
        User user = userDAO.get(id);
        if(user != null) {
            return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        }
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userDAO.getAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
        }
        return dtos;
    }

    @Override
    public UserDTO loginUser(String username, String rawPassword) {
        User user = userDAO.getUserByUsername(username);
        if (user != null) {
            if (PasswordUtil.checkPassword(rawPassword, user.getPassword())) {
                return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
            } else {
                throw new LoginException("Invalid Password");
            }
        }
        throw new LoginException("User not found");
    }
}
