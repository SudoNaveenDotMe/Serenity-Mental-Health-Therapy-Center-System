package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapistBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapistDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapistDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Therapist;
import lk.ijse.the_seranity_mental_health_therapy_center.exception.RegistrationException;
import lk.ijse.the_seranity_mental_health_therapy_center.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPIST);

    @Override
    public boolean saveTherapist(TherapistDTO dto) {
        if (!RegexUtil.isValidPhone(dto.getContact())) throw new RegistrationException("Invalid Phone Format");
        return therapistDAO.add(new Therapist(dto.getId(), dto.getName(), dto.getSpecialization(), dto.getContact(), null));
    }

    @Override
    public boolean updateTherapist(TherapistDTO dto) {
        if (!RegexUtil.isValidPhone(dto.getContact())) throw new RegistrationException("Invalid Phone Format");
        return therapistDAO.update(new Therapist(dto.getId(), dto.getName(), dto.getSpecialization(), dto.getContact(), null));
    }

    @Override
    public boolean deleteTherapist(String id) {
        return therapistDAO.delete(id);
    }

    @Override
    public TherapistDTO getTherapist(String id) {
        Therapist t = therapistDAO.get(id);
        if (t != null) {
            return new TherapistDTO(t.getId(), t.getName(), t.getSpecialization(), t.getContact());
        }
        return null;
    }

    @Override
    public List<TherapistDTO> getAllTherapists() {
        List<Therapist> therapists = therapistDAO.getAll();
        List<TherapistDTO> dtos = new ArrayList<>();
        for (Therapist t : therapists) {
            dtos.add(new TherapistDTO(t.getId(), t.getName(), t.getSpecialization(), t.getContact()));
        }
        return dtos;
    }
}
