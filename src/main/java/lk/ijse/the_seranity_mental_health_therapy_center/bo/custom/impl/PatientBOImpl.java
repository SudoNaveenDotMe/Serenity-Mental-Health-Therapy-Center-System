package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.PatientBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PatientDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PatientDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Patient;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;
import lk.ijse.the_seranity_mental_health_therapy_center.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class PatientBOImpl implements PatientBO {

    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PATIENT);

    @Override
    public boolean savePatient(PatientDTO dto) {
        if (!RegexUtil.isValidEmail(dto.getEmail())) throw new RegistrationException("Invalid Email Format");
        if (!RegexUtil.isValidPhone(dto.getPhone())) throw new RegistrationException("Invalid Phone Format");
        if (!RegexUtil.isNotEmpty(dto.getName())) throw new RegistrationException("Name is required");

        return patientDAO.add(new Patient(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getMedicalHistory(), null, null));
    }

    @Override
    public boolean updatePatient(PatientDTO dto) {
        if (!RegexUtil.isValidEmail(dto.getEmail())) throw new RegistrationException("Invalid Email Format");
        if (!RegexUtil.isValidPhone(dto.getPhone())) throw new RegistrationException("Invalid Phone Format");
        return patientDAO.update(new Patient(dto.getId(), dto.getName(), dto.getEmail(), dto.getPhone(), dto.getMedicalHistory(), null, null));
    }

    @Override
    public boolean deletePatient(String id) {
        return patientDAO.delete(id);
    }

    @Override
    public PatientDTO getPatient(String id) {
        Patient patient = patientDAO.get(id);
        if (patient != null) {
            return new PatientDTO(patient.getId(), patient.getName(), patient.getEmail(), patient.getPhone(), patient.getMedicalHistory());
        }
        return null;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientDAO.getAll();
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient p : patients) {
            dtos.add(new PatientDTO(p.getId(), p.getName(), p.getEmail(), p.getPhone(), p.getMedicalHistory()));
        }
        return dtos;
    }

    @Override
    public List<PatientDTO> getPatientsEnrolledInAllPrograms() {
        List<Patient> patients = patientDAO.getPatientsEnrolledInAllPrograms();
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient p : patients) {
            dtos.add(new PatientDTO(p.getId(), p.getName(), p.getEmail(), p.getPhone(), p.getMedicalHistory()));
        }
        return dtos;
    }

    @Override
    public List<Object[]> getPatientsWithEnrolledPrograms() {
        return patientDAO.getPatientsWithEnrolledPrograms();
    }
}
