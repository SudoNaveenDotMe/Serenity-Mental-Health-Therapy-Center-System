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
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.PaymentBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PaymentDTO;

import java.time.LocalDate;
import java.util.List;

public class PaymentController {

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private TableColumn<PaymentDTO, Double> colAmount;

    @FXML
    private TableColumn<PaymentDTO, LocalDate> colDate;

    @FXML
    private TableColumn<PaymentDTO, String> colId;

    @FXML
    private TableColumn<PaymentDTO, String> colRegistrationId;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private DatePicker dtpDate;

    @FXML
    private TableView<PaymentDTO> tblPayments;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtRegistrationId;

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    @FXML
    public void initialize() {
        cmbStatus.getItems().addAll("Completed", "Pending", "Failed");
        setCellValueFactory();
        loadAllPayments();
        setTableSelection();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
    }

    private void loadAllPayments() {
        try {
            List<PaymentDTO> allPayments = paymentBO.getAllPayments();
            ObservableList<PaymentDTO> obList = FXCollections.observableArrayList(allPayments);
            tblPayments.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setTableSelection() {
        tblPayments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtId.setText(newSelection.getId());
                txtAmount.setText(String.valueOf(newSelection.getAmount()));
                dtpDate.setValue(newSelection.getPaymentDate());
                cmbStatus.setValue(newSelection.getStatus());
                txtRegistrationId.setText(newSelection.getRegistrationId());
            }
        });
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtId.clear();
        txtAmount.clear();
        dtpDate.setValue(null);
        cmbStatus.getSelectionModel().clearSelection();
        txtRegistrationId.clear();
        tblPayments.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtId.getText();
        if (id.isEmpty()) return;

        try {
            boolean isDeleted = paymentBO.deletePayment(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted!").show();
                loadAllPayments();
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
            PaymentDTO dto = collectData();
            if (dto != null) {
                boolean isSaved = paymentBO.savePayment(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Processed!").show();
                    loadAllPayments();
                    lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                    btnClearOnAction(null);
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            PaymentDTO dto = collectData();
            if (dto != null) {
                boolean isUpdated = paymentBO.updatePayment(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Updated!").show();
                    loadAllPayments();
                    lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel.getInstance().refreshAsync();
                    btnClearOnAction(null);
                }
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private PaymentDTO collectData() {
        String id = txtId.getText();
        String registrationId = txtRegistrationId.getText();
        LocalDate date = dtpDate.getValue();
        String status = cmbStatus.getValue();

        if (id.isEmpty() || registrationId.isEmpty() || date == null || status == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return null;
        }

        try {
            Double amount = Double.parseDouble(txtAmount.getText());
            return new PaymentDTO(id, amount, date, status, registrationId);
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Amount must be a valid number!").show();
            return null;
        }
    }
}
