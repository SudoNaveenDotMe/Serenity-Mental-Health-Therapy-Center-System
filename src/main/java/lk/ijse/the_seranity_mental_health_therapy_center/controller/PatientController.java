package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PatientDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;

import java.util.List;

public class PatientController {

    @FXML
    private TableColumn<PatientDTO, String> colEmail;

    @FXML
    private TableColumn<PatientDTO, String> colHistory;

    @FXML
    private TableColumn<PatientDTO, String> colId;

    @FXML
    private TableColumn<PatientDTO, String> colName;

    @FXML
    private TableColumn<PatientDTO, String> colPhone;

    @FXML
    private TableView<PatientDTO> tblPatients;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextArea txtHistory;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PATIENT);

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllPatients();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
    }

    private void loadAllPatients() {
        try {
            List<PatientDTO> allPatients = patientBO.getAllPatients();
            ObservableList<PatientDTO> obList = FXCollections.observableArrayList(allPatients);
            tblPatients.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblPatients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtEmail.setText(newSelection.getEmail());
                txtPhone.setText(newSelection.getPhone());
                txtHistory.setText(newSelection.getMedicalHistory());
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtHistory.clear();
        tblPatients.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = patientBO.deletePatient(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Deleted!").show();
                loadAllPatients();
                lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                btnClearOnAction(null);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String history = txtHistory.getText();

        try {
            PatientDTO dto = new PatientDTO(id, name, email, phone, history);
            boolean isSaved = patientBO.savePatient(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Saved!").show();
                loadAllPatients();
                lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                btnClearOnAction(null);
            }
        } catch (RegistrationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String history = txtHistory.getText();

        try {
            PatientDTO dto = new PatientDTO(id, name, email, phone, history);
            boolean isUpdated = patientBO.updatePatient(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Updated!").show();
                loadAllPatients();
                lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                btnClearOnAction(null);
            }
        } catch (RegistrationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
