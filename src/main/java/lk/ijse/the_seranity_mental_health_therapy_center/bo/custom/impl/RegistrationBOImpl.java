package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.RegistrationBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.RegistrationDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.RegistrationDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapyProgram;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    private final RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.REGISTRATION);
    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);
    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);

    @Override
    public boolean saveRegistration(RegistrationDTO dto) {
        Patient patient = patientDAO.get(dto.getPatientId());
        if (patient == null) throw new RegistrationException("Patient not found!");

        TherapyProgram program = therapyProgramDAO.get(dto.getProgramId());
        if (program == null) throw new RegistrationException("Program not found!");

        Registration registration = new Registration(dto.getId(), dto.getRegistrationDate(), patient, program, null);
        return registrationDAO.add(registration);
    }

    @Override
    public boolean updateRegistration(RegistrationDTO dto) {
        Patient patient = patientDAO.get(dto.getPatientId());
        TherapyProgram program = therapyProgramDAO.get(dto.getProgramId());
        Registration registration = new Registration(dto.getId(), dto.getRegistrationDate(), patient, program, null);
        return registrationDAO.update(registration);
    }

    @Override
    public boolean deleteRegistration(String id) {
        return registrationDAO.delete(id);
    }

    @Override
    public RegistrationDTO getRegistration(String id) {
        Registration r = registrationDAO.get(id);
        if (r != null) {
            return new RegistrationDTO(r.getId(), r.getRegistrationDate(), r.getPatient().getId(), r.getProgram().getProgramId());
        }
        return null;
    }

    @Override
    public List<RegistrationDTO> getAllRegistrations() {
        List<Registration> list = registrationDAO.getAll();
        List<RegistrationDTO> dtos = new ArrayList<>();
        for (Registration r : list) {
            dtos.add(new RegistrationDTO(r.getId(), r.getRegistrationDate(), r.getPatient().getId(), r.getProgram().getProgramId()));
        }
        return dtos;
    }
}
