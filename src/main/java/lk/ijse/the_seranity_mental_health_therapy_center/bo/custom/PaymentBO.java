package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.SuperBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PaymentDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO dto);
    boolean updatePayment(PaymentDTO dto);
    boolean deletePayment(String id);
    PaymentDTO getPayment(String id);
    List<PaymentDTO> getAllPayments();
}
