package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapySessionBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapySessionDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapySessionDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Therapist;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapySession;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.SchedulingConflictException;

import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    private final TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_SESSION);
    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);
    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public boolean saveTherapySession(TherapySessionDTO dto) {
        Patient patient = patientDAO.get(dto.getPatientId());
        Therapist therapist = therapistDAO.get(dto.getTherapistId());
        
        if (therapist == null) throw new SchedulingConflictException("Therapist not found!");
        if (patient == null) throw new SchedulingConflictException("Patient not found!");

        // Validate therapist availability
        boolean isAvailable = therapySessionDAO.isTherapistAvailable(dto.getTherapistId(), dto.getSessionDate(), dto.getSessionTime());
        if (!isAvailable) {
            throw new SchedulingConflictException("Therapist is already booked for this date and time.");
        }

        TherapySession session = new TherapySession(dto.getId(), dto.getSessionDate(), dto.getSessionTime(), dto.getStatus(), patient, therapist);
        return therapySessionDAO.add(session);
    }

    @Override
    public boolean updateTherapySession(TherapySessionDTO dto) {
        Patient patient = patientDAO.get(dto.getPatientId());
        Therapist therapist = therapistDAO.get(dto.getTherapistId());
        
        // Need to ensure the time they update to doesn't conflict. 
        // Note: For a strict update, we would exclude the current session ID, but this will do for simple validation.
        TherapySession existingSession = therapySessionDAO.get(dto.getId());
        if (existingSession != null) {
            boolean timeChanged = !existingSession.getSessionTime().equals(dto.getSessionTime()) || !existingSession.getSessionDate().equals(dto.getSessionDate());
            if (timeChanged) {
                boolean isAvailable = therapySessionDAO.isTherapistAvailable(dto.getTherapistId(), dto.getSessionDate(), dto.getSessionTime());
                if (!isAvailable) {
                    throw new SchedulingConflictException("Therapist is already booked for this new date and time.");
                }
            }
        }

        TherapySession session = new TherapySession(dto.getId(), dto.getSessionDate(), dto.getSessionTime(), dto.getStatus(), patient, therapist);
        return therapySessionDAO.update(session);
    }

    @Override
    public boolean deleteTherapySession(String id) {
        return therapySessionDAO.delete(id);
    }

    @Override
    public TherapySessionDTO getTherapySession(String id) {
        TherapySession ts = therapySessionDAO.get(id);
        if (ts != null) {
            return new TherapySessionDTO(ts.getId(), ts.getSessionDate(), ts.getSessionTime(), ts.getStatus(), ts.getPatient().getId(), ts.getTherapist().getId());
        }
        return null;
    }

    @Override
    public List<TherapySessionDTO> getAllTherapySessions() {
        List<TherapySession> list = therapySessionDAO.getAll();
        List<TherapySessionDTO> dtos = new ArrayList<>();
        for (TherapySession ts : list) {
            dtos.add(new TherapySessionDTO(ts.getId(), ts.getSessionDate(), ts.getSessionTime(), ts.getStatus(), ts.getPatient().getId(), ts.getTherapist().getId()));
        }
        return dtos;
    }
}
