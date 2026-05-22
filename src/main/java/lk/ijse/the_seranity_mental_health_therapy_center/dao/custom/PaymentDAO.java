package lk.ijse.the_seranity_mental_health_therapy_center.dao.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.dao.CrudDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment, String> {
    double getGrossRevenue();
}
