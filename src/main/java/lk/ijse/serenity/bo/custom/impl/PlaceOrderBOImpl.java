package lk.ijse.serenity.bo.custom.impl;

import lk.ijse.serenity.bo.custom.PlaceOrderBO;
import lk.ijse.serenity.bo.exception.NotFoundException;
import lk.ijse.serenity.dao.DAOFactory;
import lk.ijse.serenity.dao.DAOTypes;
import lk.ijse.serenity.dao.custom.CustomerDAO;
import lk.ijse.serenity.dao.custom.ItemDAO;
import lk.ijse.serenity.dao.custom.OrderDAO;
import lk.ijse.serenity.dao.custom.OrderDetailsDAO;
import lk.ijse.serenity.db.DBConnection;
import lk.ijse.serenity.dto.OrderDTO;
import lk.ijse.serenity.dto.OrderDetailsDTO;
import lk.ijse.serenity.entity.Customer;
import lk.ijse.serenity.entity.Item;
import lk.ijse.serenity.entity.Order;
import lk.ijse.serenity.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/11/2025 10:25 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public class PlaceOrderBOImpl implements PlaceOrderBO {
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);

    private OrderDetailsDAO orderDetailsDAO =
            DAOFactory.getInstance().getDAO(DAOTypes.ORDER_DETAILS);
    private ItemDAO itemDAO = DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);

    @Override
    public boolean placeOrder(OrderDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            Optional<Customer> optionalCustomer = customerDAO.findById(dto.getCustomerId());
            if (optionalCustomer.isEmpty()) {
                throw new NotFoundException("Customer not found");
            }

            Order order = new Order();
            order.setId(dto.getOrderId());
            order.setCustomerId(dto.getCustomerId());
            order.setOrderDate(dto.getDate());

            boolean isOrderSaved = orderDAO.save(order);
            if (isOrderSaved) {
                boolean isSuccess = saveDetailsAndUpdateItem(dto.getCartList());
                if (isSuccess) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
//            throw new Exception("Fail somthing");
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getNextId() throws SQLException {
        String lastId = orderDAO.getLastId();
        char tableChar = 'O';
        if (lastId != null) {
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableChar + "%03d", nextIdNumber);
        }
        return tableChar + "001";
    }

    private boolean saveDetailsAndUpdateItem(List<OrderDetailsDTO> detailsList) throws SQLException {
        //            for (int i = 1; i < detailsList.size() - 1; i++) {
//                OrderDetailsDTO orderDetailsDTO = detailsList.get(i);
//            }
        for (OrderDetailsDTO detailsDTO : detailsList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(detailsDTO.getOrderId());
            orderDetail.setItemId(detailsDTO.getItemId());
            orderDetail.setQuantity(detailsDTO.getQty());
            orderDetail.setPrice(BigDecimal.valueOf(detailsDTO.getPrice()));

            if (!orderDetailsDAO.save(orderDetail)) {
//                rollback
                return false;
            }

            Optional<Item> optionalItem = itemDAO.findById(detailsDTO.getItemId());
            if (optionalItem.isEmpty()) {
                throw new NotFoundException("Item not found");
            }
//            Item item = optionalItem.get();
//            int oldQuantity = item.getQuantity();

            // check quantity here

//            int newQuantity = oldQuantity - detailsDTO.getQty();
//
//            item.setQuantity(newQuantity);
//
//            itemDAO.update(item);
            boolean isItemUpdated = itemDAO.reduceQuantity(
                    detailsDTO.getItemId(),
                    detailsDTO.getQty()
            );
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }
}
