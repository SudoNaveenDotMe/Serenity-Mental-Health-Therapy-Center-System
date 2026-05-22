package lk.ijse.the_seranity_mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "therapy_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapySession {
    @Id
    private String id;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private String status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;
}
