package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.DashboardBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.*;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RecentRegistrationDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DashboardBOImpl implements DashboardBO {

    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);
    private final TherapySessionDAO sessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_SESSION);
    private final TherapyProgramDAO programDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    private final RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.REGISTRATION);

    @Override
    public long getPatientCount() {
        return patientDAO.getPatientCount();
    }

    @Override
    public long getTodaySessionCount() {
        return sessionDAO.getSessionCountByDate(LocalDate.now());
    }

    @Override
    public long getActiveProgramCount() {
        return programDAO.getProgramCount();
    }

    @Override
    public double getGrossRevenue() {
        return paymentDAO.getGrossRevenue();
    }

    @Override
    public Map<String, Long> getProgramPatientDistribution() {
        Map<String, Long> distribution = new LinkedHashMap<>();
        List<Object[]> rawList = programDAO.getProgramPatientDistribution();
        for (Object[] row : rawList) {
            String programName = (String) row[0];
            Long count = (Long) row[1];
            distribution.put(programName, count != null ? count : 0L);
        }
        return distribution;
    }

    @Override
    public List<RecentRegistrationDTO> getRecentRegistrations() {
        List<Registration> registrations = registrationDAO.getRecentRegistrations(5);
        List<RecentRegistrationDTO> dtos = new ArrayList<>();
        for (Registration r : registrations) {
            dtos.add(new RecentRegistrationDTO(
                    r.getId(),
                    r.getPatient() != null ? r.getPatient().getName() : "Unknown",
                    r.getProgram() != null ? r.getProgram().getName() : "Unknown",
                    r.getRegistrationDate()
            ));
        }
        return dtos;
    }
}
