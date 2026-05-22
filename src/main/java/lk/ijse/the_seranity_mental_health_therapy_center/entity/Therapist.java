package lk.ijse.the_seranity_mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "therapist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Therapist {
    @Id
    private String id;
    private String name;
    private String specialization;
    private String contact;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> sessions;
}
