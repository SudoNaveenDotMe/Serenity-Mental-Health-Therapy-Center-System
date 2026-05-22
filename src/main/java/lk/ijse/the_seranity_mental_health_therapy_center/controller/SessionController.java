package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapySessionBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapySessionDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.SchedulingConflictException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SessionController {

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<TherapySessionDTO, LocalDate> colDate;

    @FXML
    private TableColumn<TherapySessionDTO, String> colId;

    @FXML
    private TableColumn<TherapySessionDTO, String> colPatientId;

    @FXML
    private TableColumn<TherapySessionDTO, String> colStatus;

    @FXML
    private TableColumn<TherapySessionDTO, String> colTherapistId;

    @FXML
    private TableColumn<TherapySessionDTO, LocalTime> colTime;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TableView<TherapySessionDTO> tblSessions;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTime;

    private final TherapySessionBO sessionBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPY_SESSION);

    @FXML
    public void initialize() {
        cmbStatus.getItems().addAll("Scheduled", "Completed", "Cancelled");
        setCellValueFactory();
        loadAllSessions();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("sessionTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadAllSessions() {
        try {
            List<TherapySessionDTO> allSessions = sessionBO.getAllTherapySessions();
            ObservableList<TherapySessionDTO> obList = FXCollections.observableArrayList(allSessions);
            tblSessions.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblSessions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtPatientId.setText(newSelection.getPatientId());
                txtTherapistId.setText(newSelection.getTherapistId());
                dtpDate.setValue(newSelection.getSessionDate());
                txtTime.setText(newSelection.getSessionTime().toString());
                cmbStatus.setValue(newSelection.getStatus());
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        txtPatientId.clear();
        txtTherapistId.clear();
        dtpDate.setValue(null);
        txtTime.clear();
        cmbStatus.getSelectionModel().clearSelection();
        tblSessions.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = sessionBO.deleteTherapySession(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Session Deleted!").show();
                loadAllSessions();
                lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                btnClearOnAction(null);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            TherapySessionDTO dto = collectData();
            if (dto != null) {
                boolean isSaved = sessionBO.saveTherapySession(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Session Scheduled!").show();
                    loadAllSessions();
                    lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                    btnClearOnAction(null);
                }
            }
        } catch (SchedulingConflictException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            TherapySessionDTO dto = collectData();
            if (dto != null) {
                boolean isUpdated = sessionBO.updateTherapySession(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Session Updated!").show();
                    loadAllSessions();
                    lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                    btnClearOnAction(null);
                }
            }
        } catch (SchedulingConflictException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private TherapySessionDTO collectData() {
        String id = txtId.getText();
        String patientId = txtPatientId.getText();
        String therapistId = txtTherapistId.getText();
        LocalDate date = dtpDate.getValue();
        String status = cmbStatus.getValue();

        if (id.isEmpty() || patientId.isEmpty() || therapistId.isEmpty() || date == null || status == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return null;
        }

        try {
            LocalTime time = LocalTime.parse(txtTime.getText());
            return new TherapySessionDTO(id, date, time, status, patientId, therapistId);
        } catch (DateTimeParseException e) {
            new Alert(Alert.AlertType.WARNING, "Invalid time format! Use HH:MM").show();
            return null;
        }
    }
}
