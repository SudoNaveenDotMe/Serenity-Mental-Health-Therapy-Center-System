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
 * Created: 7/1/2025 10:27 AM
 * Project: Supermarket-layered
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "item")
public class Item {
    @Id
    @Column(name = "item_code")
    private String id;

    @Column(length = 100)
    private String name;

    private int quantity;
//    private double price;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}
