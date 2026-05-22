package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapistDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;

import java.util.List;

public class TherapistController {

    @FXML
    private TableColumn<TherapistDTO, String> colContact;

    @FXML
    private TableColumn<TherapistDTO, String> colId;

    @FXML
    private TableColumn<TherapistDTO, String> colName;

    @FXML
    private TableColumn<TherapistDTO, String> colSpecialization;

    @FXML
    private TableView<TherapistDTO> tblTherapists;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSpecialization;

    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPIST);

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllTherapists();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllTherapists() {
        try {
            List<TherapistDTO> allTherapists = therapistBO.getAllTherapists();
            ObservableList<TherapistDTO> obList = FXCollections.observableArrayList(allTherapists);
            tblTherapists.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblTherapists.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtName.setText(newSelection.getName());
                txtSpecialization.setText(newSelection.getSpecialization());
                txtContact.setText(newSelection.getContact());
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtSpecialization.clear();
        txtContact.clear();
        tblTherapists.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = therapistBO.deleteTherapist(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Deleted!").show();
                loadAllTherapists();
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
        String specialization = txtSpecialization.getText();
        String contact = txtContact.getText();

        try {
            TherapistDTO dto = new TherapistDTO(id, name, specialization, contact);
            boolean isSaved = therapistBO.saveTherapist(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Saved!").show();
                loadAllTherapists();
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
        String specialization = txtSpecialization.getText();
        String contact = txtContact.getText();

        try {
            TherapistDTO dto = new TherapistDTO(id, name, specialization, contact);
            boolean isUpdated = therapistBO.updateTherapist(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Therapist Updated!").show();
                loadAllTherapists();
                btnClearOnAction(null);
            }
        } catch (RegistrationException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
