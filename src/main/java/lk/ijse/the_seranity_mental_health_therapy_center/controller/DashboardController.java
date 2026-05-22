package lk.ijse.the_seranity_mental_health_therapy_center.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RecentRegistrationDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.UserDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.util.DashboardDataModel;
import lk.ijse.the_seranity_mental_health_therapy_center.util.SessionContext;

import java.time.LocalDate;
import java.util.Map;

public class DashboardController {

    @FXML
    private Label lblWelcomeGreeting;

    @FXML
    private Label lblTotalPatients;

    @FXML
    private Label lblTodaySessions;

    @FXML
    private Label lblActivePrograms;

    @FXML
    private Label lblGrossRevenue;

    @FXML
    private BarChart<String, Number> barChartEnrollment;

    @FXML
    private TableView<RecentRegistrationDTO> tblRecentRegistrations;

    @FXML
    private TableColumn<RecentRegistrationDTO, String> colRegId;

    @FXML
    private TableColumn<RecentRegistrationDTO, String> colPatientName;

    @FXML
    private TableColumn<RecentRegistrationDTO, String> colProgramName;

    @FXML
    private TableColumn<RecentRegistrationDTO, LocalDate> colRegDate;

    public void initialize() {
        // 1. Set dynamic welcome greeting based on logged in user session
        UserDTO user = SessionContext.getInstance().getLoggedUser();
        if (user != null) {
            String roleName = user.getRole() != null ? user.getRole().toUpperCase() : "USER";
            lblWelcomeGreeting.setText("Welcome Back, " + user.getUsername() + " (" + roleName + ")");
        } else {
            lblWelcomeGreeting.setText("Welcome Back, Administrator");
        }

        // 2. Set up cell value factories for recent registrations table
        colRegId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));

        // 3. Bind properties for real-time reactive updates
        lblTotalPatients.textProperty().bind(DashboardDataModel.getInstance().totalPatientsProperty().asString());
        lblTodaySessions.textProperty().bind(DashboardDataModel.getInstance().todaySessionsProperty().asString());
        lblActivePrograms.textProperty().bind(DashboardDataModel.getInstance().activeProgramsProperty().asString());
        lblGrossRevenue.textProperty().bind(Bindings.format("LKR %,.2f", DashboardDataModel.getInstance().grossRevenueProperty()));

        // 4. Bind registrations list to TableView items
        tblRecentRegistrations.setItems(DashboardDataModel.getInstance().getRecentRegistrations());

        // 5. Connect program distribution dataset to the BarChart with a change listener
        DashboardDataModel.getInstance().getProgramDistribution().addListener((ListChangeListener<Map.Entry<String, Long>>) c -> {
            updateChart();
        });
        updateChart();

        // 6. Explicitly trigger a refresh on model during load to capture any out-of-sync edits
        DashboardDataModel.getInstance().refreshAsync();
    }

    private void updateChart() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Long> entry : DashboardDataModel.getInstance().getProgramDistribution()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        barChartEnrollment.getData().clear();
        barChartEnrollment.getData().add(series);
    }
}
