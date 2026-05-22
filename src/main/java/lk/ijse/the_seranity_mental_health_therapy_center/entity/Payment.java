package lk.ijse.the_seranity_mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String id;
    private Double amount;
    private LocalDate paymentDate;
    private String status;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;
}
