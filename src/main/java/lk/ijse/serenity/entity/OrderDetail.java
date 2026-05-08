package lk.ijse.serenity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 7/1/2025 10:28 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @ManyToOne
    @JoinColumn(name = "item_code")
    public Item item;
    public int quantity;
    public BigDecimal price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
