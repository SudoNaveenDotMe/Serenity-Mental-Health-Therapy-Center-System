package lk.ijse.the_seranity_mental_health_therapy_center.util;

import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;

public class SessionContext {
    private static SessionContext instance;
    private UserDTO loggedUser;

    private SessionContext() {}

    public static SessionContext getInstance() {
        if (instance == null) {
            instance = new SessionContext();
        }
        return instance;
    }

    public UserDTO getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserDTO loggedUser) {
        this.loggedUser = loggedUser;
    }
}
