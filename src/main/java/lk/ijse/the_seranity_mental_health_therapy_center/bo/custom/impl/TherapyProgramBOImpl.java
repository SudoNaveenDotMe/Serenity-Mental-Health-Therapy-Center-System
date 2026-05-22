package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.TherapyProgramBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.TherapyProgramDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.TherapyProgramDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.THERAPY_PROGRAM);

    @Override
    public boolean saveTherapyProgram(TherapyProgramDTO dto) {
        return therapyProgramDAO.add(new TherapyProgram(dto.getProgramId(), dto.getName(), dto.getDuration(), dto.getFee(), null));
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgramDTO dto) {
        return therapyProgramDAO.update(new TherapyProgram(dto.getProgramId(), dto.getName(), dto.getDuration(), dto.getFee(), null));
    }

    @Override
    public boolean deleteTherapyProgram(String id) {
        return therapyProgramDAO.delete(id);
    }

    @Override
    public TherapyProgramDTO getTherapyProgram(String id) {
        TherapyProgram tp = therapyProgramDAO.get(id);
        if (tp != null) {
            return new TherapyProgramDTO(tp.getProgramId(), tp.getName(), tp.getDuration(), tp.getFee());
        }
        return null;
    }

    @Override
    public List<TherapyProgramDTO> getAllTherapyPrograms() {
        List<TherapyProgram> list = therapyProgramDAO.getAll();
        List<TherapyProgramDTO> dtos = new ArrayList<>();
        for (TherapyProgram tp : list) {
            dtos.add(new TherapyProgramDTO(tp.getProgramId(), tp.getName(), tp.getDuration(), tp.getFee()));
        }
        return dtos;
    }
}
