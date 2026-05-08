package lk.ijse.serenity.dao.custom;

import lk.ijse.serenity.dao.CrudDAO;
import lk.ijse.serenity.entity.Item;

import java.sql.SQLException;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/1/2025 11:14 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

public interface ItemDAO extends CrudDAO<Item> {
    boolean reduceQuantity(String id, int qty) throws SQLException;
}
