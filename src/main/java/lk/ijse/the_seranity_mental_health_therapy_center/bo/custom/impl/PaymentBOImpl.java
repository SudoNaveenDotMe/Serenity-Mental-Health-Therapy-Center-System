package lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.impl;

import lk.ijse.the_seranity_mental_health_therapy_center.bo.custom.PaymentBO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.DAOFactory;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.PaymentDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dao.custom.RegistrationDAO;
import lk.ijse.the_seranity_mental_health_therapy_center.dto.PaymentDTO;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Payment;
import lk.ijse.the_seranity_mental_health_therapy_center.entity.Registration;

import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    private final RegistrationDAO registrationDAO = (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.REGISTRATION);

    @Override
    public boolean savePayment(PaymentDTO dto) {
        Registration registration = registrationDAO.get(dto.getRegistrationId());
        Payment payment = new Payment(dto.getId(), dto.getAmount(), dto.getPaymentDate(), dto.getStatus(), registration);
        return paymentDAO.add(payment);
    }

    @Override
    public boolean updatePayment(PaymentDTO dto) {
        Registration registration = registrationDAO.get(dto.getRegistrationId());
        Payment payment = new Payment(dto.getId(), dto.getAmount(), dto.getPaymentDate(), dto.getStatus(), registration);
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String id) {
        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDTO getPayment(String id) {
        Payment p = paymentDAO.get(id);
        if (p != null) {
            return new PaymentDTO(p.getId(), p.getAmount(), p.getPaymentDate(), p.getStatus(), p.getRegistration().getId());
        }
        return null;
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<Payment> list = paymentDAO.getAll();
        List<PaymentDTO> dtos = new ArrayList<>();
        for (Payment p : list) {
            dtos.add(new PaymentDTO(p.getId(), p.getAmount(), p.getPaymentDate(), p.getStatus(), p.getRegistration().getId()));
        }
        return dtos;
    }
}
