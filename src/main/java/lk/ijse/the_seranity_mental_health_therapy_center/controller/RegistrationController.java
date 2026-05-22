package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.RegistrationBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RegistrationDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;

import java.time.LocalDate;
import java.util.List;

public class RegistrationController {

    @FXML
    private TableColumn<RegistrationDTO, LocalDate> colDate;

    @FXML
    private TableColumn<RegistrationDTO, String> colId;

    @FXML
    private TableColumn<RegistrationDTO, String> colPatientId;

    @FXML
    private TableColumn<RegistrationDTO, String> colProgramId;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TableView<RegistrationDTO> tblRegistrations;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtProgramId;

    private final RegistrationBO registrationBO = (RegistrationBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.REGISTRATION);

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllRegistrations();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
    }

    private void loadAllRegistrations() {
        try {
            List<RegistrationDTO> allRegistrations = registrationBO.getAllRegistrations();
            ObservableList<RegistrationDTO> obList = FXCollections.observableArrayList(allRegistrations);
            tblRegistrations.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblRegistrations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                dtpDate.setValue(newSelection.getRegistrationDate());
                txtPatientId.setText(newSelection.getPatientId());
                txtProgramId.setText(newSelection.getProgramId());
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        dtpDate.setValue(null);
        txtPatientId.clear();
        txtProgramId.clear();
        tblRegistrations.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = registrationBO.deleteRegistration(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Registration Deleted!").show();
                loadAllRegistrations();
                btnClearOnAction(null);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtId.getText();
        LocalDate date = dtpDate.getValue();
        String patientId = txtPatientId.getText();
        String programId = txtProgramId.getText();

        if (id.isEmpty() || date == null || patientId.isEmpty() || programId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        try {
            RegistrationDTO dto = new RegistrationDTO(id, date, patientId, programId);
            boolean isSaved = registrationBO.saveRegistration(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Patient Registered!").show();
                loadAllRegistrations();
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
        LocalDate date = dtpDate.getValue();
        String patientId = txtPatientId.getText();
        String programId = txtProgramId.getText();

        if (id.isEmpty() || date == null || patientId.isEmpty() || programId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        try {
            RegistrationDTO dto = new RegistrationDTO(id, date, patientId, programId);
            boolean isUpdated = registrationBO.updateRegistration(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Registration Updated!").show();
                loadAllRegistrations();
                btnClearOnAction(null);
            }
        } catch (RegistrationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
