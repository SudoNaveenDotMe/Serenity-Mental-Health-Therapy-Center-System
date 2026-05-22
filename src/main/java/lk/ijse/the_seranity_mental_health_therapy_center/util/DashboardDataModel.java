package lk.ijse.the_seranity_mental_health_therapy_center.util;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.BOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.DashboardBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RecentRegistrationDTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardDataModel {
    private static DashboardDataModel instance;

    private final SimpleLongProperty totalPatients = new SimpleLongProperty(0);
    private final SimpleLongProperty todaySessions = new SimpleLongProperty(0);
    private final SimpleLongProperty activePrograms = new SimpleLongProperty(0);
    private final SimpleDoubleProperty grossRevenue = new SimpleDoubleProperty(0.0);

    private final ObservableList<RecentRegistrationDTO> recentRegistrations = FXCollections.observableArrayList();
    private final ObservableList<Map.Entry<String, Long>> programDistribution = FXCollections.observableArrayList();

    private final DashboardBO dashboardBO = (DashboardBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DASHBOARD);
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    private DashboardDataModel() {
        refreshAsync();
    }

    public static synchronized DashboardDataModel getInstance() {
        if (instance == null) {
            instance = new DashboardDataModel();
        }
        return instance;
    }

    public SimpleLongProperty totalPatientsProperty() { return totalPatients; }
    public SimpleLongProperty todaySessionsProperty() { return todaySessions; }
    public SimpleLongProperty activeProgramsProperty() { return activePrograms; }
    public SimpleDoubleProperty grossRevenueProperty() { return grossRevenue; }
    
    public ObservableList<RecentRegistrationDTO> getRecentRegistrations() {
        return recentRegistrations;
    }

    public ObservableList<Map.Entry<String, Long>> getProgramDistribution() {
        return programDistribution;
    }

    public void refreshAsync() {
        executorService.submit(() -> {
            try {
                long patients = dashboardBO.getPatientCount();
                long sessions = dashboardBO.getTodaySessionCount();
                long programs = dashboardBO.getActiveProgramCount();
                double revenue = dashboardBO.getGrossRevenue();
                List<RecentRegistrationDTO> recentList = dashboardBO.getRecentRegistrations();
                Map<String, Long> distribution = dashboardBO.getProgramPatientDistribution();

                Platform.runLater(() -> {
                    totalPatients.set(patients);
                    todaySessions.set(sessions);
                    activePrograms.set(programs);
                    grossRevenue.set(revenue);

                    recentRegistrations.setAll(recentList);
                    programDistribution.setAll(distribution.entrySet());
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
