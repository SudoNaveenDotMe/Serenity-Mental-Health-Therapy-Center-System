package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;

public class MainLayoutController {

    @FXML
    private BorderPane contentPane;

    @FXML
    private Label lblRole;
    
    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnProgram;

    private String userRole;

    @FXML
    public void initialize() {
        loadContent("/view/dashboard-view.fxml");
    }

    public void setUserRole(String role) {
        this.userRole = role;
        lblRole.setText("Logged in as: " + role);
        
        // Hide Admin buttons if role is RECEPTIONIST
        if ("RECEPTIONIST".equalsIgnoreCase(role)) {
            btnTherapist.setVisible(false);
            btnTherapist.setManaged(false);
            btnProgram.setVisible(false);
            btnProgram.setManaged(false);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        loadContent("/view/dashboard-view.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource != null) {
                Parent pane = FXMLLoader.load(resource);
                contentPane.setCenter(pane);
            } else {
                System.err.println("View not found: " + fxmlPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        loadContent("/view/patient-view.fxml");
    }

    @FXML
    void btnTherapistOnAction(ActionEvent event) {
        loadContent("/view/therapist-view.fxml");
    }

    @FXML
    void btnProgramOnAction(ActionEvent event) {
        loadContent("/view/program-view.fxml");
    }

    @FXML
    void btnSessionOnAction(ActionEvent event) {
        loadContent("/view/session-view.fxml");
    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) {
        loadContent("/view/registration-view.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadContent("/view/payment-view.fxml");
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/view/login-view.fxml");
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
