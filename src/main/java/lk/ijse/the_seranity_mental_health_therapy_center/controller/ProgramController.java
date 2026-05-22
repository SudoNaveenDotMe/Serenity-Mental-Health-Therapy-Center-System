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
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapyProgramDTO;

import java.util.List;

public class ProgramController {

    @FXML
    private TableColumn<TherapyProgramDTO, String> colDuration;

    @FXML
    private TableColumn<TherapyProgramDTO, Double> colFee;

    @FXML
    private TableColumn<TherapyProgramDTO, String> colId;

    @FXML
    private TableColumn<TherapyProgramDTO, String> colName;

    @FXML
    private TableView<TherapyProgramDTO> tblPrograms;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    private final TherapyProgramBO programBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.THERAPY_PROGRAM);

    @FXML
    public void initialize() {
        setCellValueFactory();
        loadAllPrograms();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void loadAllPrograms() {
        try {
            List<TherapyProgramDTO> allPrograms = programBO.getAllTherapyPrograms();
            ObservableList<TherapyProgramDTO> obList = FXCollections.observableArrayList(allPrograms);
            tblPrograms.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblPrograms.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getProgramId());
                txtName.setText(newSelection.getName());
                txtDuration.setText(newSelection.getDuration());
                txtFee.setText(String.valueOf(newSelection.getFee()));
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        txtName.clear();
        txtDuration.clear();
        txtFee.clear();
        tblPrograms.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = programBO.deleteTherapyProgram(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Program Deleted!").show();
                loadAllPrograms();
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
        String duration = txtDuration.getText();
        
        try {
            Double fee = Double.parseDouble(txtFee.getText());
            TherapyProgramDTO dto = new TherapyProgramDTO(id, name, duration, fee);
            boolean isSaved = programBO.saveTherapyProgram(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Program Saved!").show();
                loadAllPrograms();
                btnClearOnAction(null);
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Fee must be a valid number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String duration = txtDuration.getText();
        
        try {
            Double fee = Double.parseDouble(txtFee.getText());
            TherapyProgramDTO dto = new TherapyProgramDTO(id, name, duration, fee);
            boolean isUpdated = programBO.updateTherapyProgram(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Program Updated!").show();
                loadAllPrograms();
                btnClearOnAction(null);
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Fee must be a valid number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
