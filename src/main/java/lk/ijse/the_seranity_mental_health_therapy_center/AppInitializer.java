package lk.ijse.the_seranity_mental_health_therapy_center;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.UserBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;

public class AppInitializer extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        seedDefaultUser();

        URL resource = getClass().getResource("/view/login-view.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void seedDefaultUser() {
        UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
        try {
            if (userBO.getAllUsers().isEmpty()) {
                System.out.println("No users found. Creating default Admin user...");
                userBO.saveUser(new UserDTO("U001", "admin", "admin123", "ADMIN"));
                System.out.println("Default user created! Username: admin | Password: admin123");
            }
        } catch (Exception e) {
            System.err.println("Failed to seed default user: " + e.getMessage());
        }
    }
}
