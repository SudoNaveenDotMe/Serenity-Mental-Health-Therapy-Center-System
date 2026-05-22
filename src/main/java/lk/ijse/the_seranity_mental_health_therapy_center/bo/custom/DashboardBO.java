package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RecentRegistrationDTO;
import java.util.List;
import java.util.Map;

public interface DashboardBO extends SuperBO {
    long getPatientCount();
    long getTodaySessionCount();
    long getActiveProgramCount();
    double getGrossRevenue();
    Map<String, Long> getProgramPatientDistribution();
    List<RecentRegistrationDTO> getRecentRegistrations();
}
