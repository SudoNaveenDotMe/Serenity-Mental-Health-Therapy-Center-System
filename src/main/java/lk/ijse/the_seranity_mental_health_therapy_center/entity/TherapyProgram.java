package lk.ijse.the_seranity_mental_health_therapy_center.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "therapy_program")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TherapyProgram {
    @Id
    private String programId; // MT1001, etc.
    private String name;
    private String duration;
    private Double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Registration> registrations;
}
