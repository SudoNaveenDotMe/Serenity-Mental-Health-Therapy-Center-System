package lk.ijse.serenity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "cus_id")
    private Customer customer;

    private Date orderDate;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
