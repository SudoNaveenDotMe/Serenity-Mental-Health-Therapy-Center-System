package lk.ijse.the_seranity_mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "registration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
    @Id
    private String id;
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TherapyProgram program;

    @OneToOne(mappedBy = "registration", cascade = CascadeType.ALL)
    private Payment payment;
}
