package lk.ijse.the_seranity_mental_health_therapy_center.dao;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        USER, THERAPIST, THERAPY_PROGRAM, PATIENT, REGISTRATION, PAYMENT, THERAPY_SESSION
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case USER:
                return new UserDAOImpl();
            case THERAPIST:
                return new TherapistDAOImpl();
            case THERAPY_PROGRAM:
                return new TherapyProgramDAOImpl();
            case PATIENT:
                return new PatientDAOImpl();
            case REGISTRATION:
                return new RegistrationDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case THERAPY_SESSION:
                return new TherapySessionDAOImpl();
            default:
                return null;
        }
    }
}
