package lk.ijse.the_seranity_mental_health_therapy_center.bo;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        USER, THERAPIST, THERAPY_PROGRAM, PATIENT, REGISTRATION, PAYMENT, THERAPY_SESSION, DASHBOARD
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case USER:
                return new UserBOImpl();
            case THERAPIST:
                return new TherapistBOImpl();
            case THERAPY_PROGRAM:
                return new TherapyProgramBOImpl();
            case PATIENT:
                return new PatientBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case THERAPY_SESSION:
                return new TherapySessionBOImpl();
            case DASHBOARD:
                return new DashboardBOImpl();
            default:
                return null;
        }
    }
}
