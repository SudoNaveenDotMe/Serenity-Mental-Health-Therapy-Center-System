package lk.ijse.serenity.bo.custom;

import lk.ijse.serenity.bo.SuperBO;
import lk.ijse.serenity.bo.exception.DuplicateException;
import lk.ijse.serenity.bo.exception.InUseException;
import lk.ijse.serenity.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/11/2025 10:22 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public interface CustomerBO extends SuperBO {
    List<CustomerDTO> getAllCustomer() throws SQLException;

    void saveCustomer(CustomerDTO dto) throws DuplicateException, Exception;

    void updateCustomer(CustomerDTO dto) throws SQLException;

    boolean deleteCustomer(String id) throws InUseException, Exception;

    String getNextId() throws SQLException;
}
