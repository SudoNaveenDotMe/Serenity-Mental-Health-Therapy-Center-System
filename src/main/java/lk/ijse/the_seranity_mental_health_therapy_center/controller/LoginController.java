package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.LoginException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            UserDTO user = userBO.loginUser(username, password);
            lk.ijse.the_seranity_mental_health_therapy_center.util.SessionContext.getInstance().setLoggedUser(user);
            
            // Navigate to main layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main-layout.fxml"));
            Parent root = loader.load();
            
            MainLayoutController controller = loader.getController();
            controller.setUserRole(user.getRole());
            
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.setTitle("Serenity Mental Health Therapy Center - Dashboard");
            stage.centerOnScreen();
            
        } catch (LoginException | java.io.IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
